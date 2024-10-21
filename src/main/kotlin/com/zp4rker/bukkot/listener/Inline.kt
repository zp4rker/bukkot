package com.zp4rker.bukkot.listener

import com.zp4rker.bukkot.extensions.unregister
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.plugin.Plugin
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

/**
 * @author zp4rker
 */
typealias Predicate<T> = (T) -> Boolean

inline fun <reified T : Event> Plugin.on(
    crossinline predicate: Predicate<T> = { true },
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
    crossinline action: AnonymousListener<T>.(T) -> Unit
) {
    listener {
        if (predicate(it)) action(it)
    }.register(this, priority, ignoreCancelled)
}

val scheduler: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()

inline fun <reified T : Event> Plugin.expect(
    crossinline predicate: Predicate<T> = { true },
    amount: Int = 1,
    timeout: Long = 0,
    crossinline timeoutAction: () -> Unit = {},
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
    crossinline action: AnonymousListener<T>.(T) -> Unit
) {
    var calls = 0
    val listener = listener {
        if (predicate(it)) {
            action(it)
            if (++calls >= amount) {
                unregister()
            }
        }
    }

    if (timeout > 0) {
        scheduler.schedule({
            if (calls < amount) {
                timeoutAction()
                listener.unregister()
            }
        }, timeout, TimeUnit.MILLISECONDS)
    }

    listener.register(this, priority, ignoreCancelled)
}

