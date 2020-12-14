package com.zp4rker.bukkot.extension.event

import com.zp4rker.bukkot.extension.PLUGIN_MANAGER
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.plugin.Plugin
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

/**
 * @author zp4rker
 */

typealias Predicate<T> = (T) -> Boolean

val scheduler: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()

interface ExtendedListener<in T : Event> : Listener {
    fun onEvent(event: T)
}

inline fun <reified T : Event> listener(crossinline action: Listener.(T) -> Unit) = object : ExtendedListener<T> {
    override fun onEvent(event: T) {
        if (event is T) action(event)
    }
}

inline fun <reified T : Event> Plugin.on(
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
    crossinline action: Listener.(T) -> Unit
): ExtendedListener<T> {
    return listener(action).also {
        it.register(this, priority, ignoreCancelled)
    }
}

inline fun <reified T : Event> Plugin.on(
    crossinline predicate: Predicate<T>,
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
    crossinline action: Listener.(T) -> Unit
): ExtendedListener<T> {
    return on(priority, ignoreCancelled) {
        if (predicate(it)) action(it)
    }
}

inline fun <reified T : Event> Plugin.expect(
    crossinline predicate: Predicate<T> = { true },
    amount: Int = 1,
    timeout: Long = 0,
    crossinline timeoutAction: () -> Unit = {},
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
    crossinline action: Listener.(T) -> Unit
): ExtendedListener<T> {
    var callCount = 0
    val listener = on<T>(priority, ignoreCancelled) {
        if (predicate(it)) {
            action(it)
            if (++callCount >= amount) {
                unregister()
            }
        }
    }

    if (timeout > 0) {
        scheduler.schedule({
            if (callCount < amount) {
                timeoutAction()
                listener.unregister()
            }
        }, timeout, TimeUnit.MILLISECONDS)
    }

    return listener
}

inline fun <reified T : Event> Listener.register(
    plugin: Plugin,
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
    noinline executor: (Listener, Event) -> Unit
) {
    PLUGIN_MANAGER.registerEvent(T::class.java, this, priority, executor, plugin, ignoreCancelled)
}

fun Listener.register(plugin: Plugin) {
    PLUGIN_MANAGER.registerEvents(this, plugin)
}

fun Listener.unregister() {
    HandlerList.unregisterAll(this)
}

inline fun <reified T : Event> ExtendedListener<T>.register(
    plugin: Plugin,
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false
) {
    register<T>(plugin, priority, ignoreCancelled) { listener, event ->
        (listener as ExtendedListener<T>).onEvent(event as T)
    }
}