package com.zp4rker.bukkot.listener

import com.zp4rker.bukkot.extensions.PMANAGER
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.plugin.EventExecutor
import org.bukkit.plugin.Plugin

/**
 * Interface for anonymous listeners
 * @see listener
 */
interface AnonymousListener<T : Event> : Listener {
    fun onEvent(event: T)
}

/**
 * Creates an instance of [AnonymousListener]
 *
 * @param predicate function to determine pre-requisites for listener to invoke action
 * @param action the function to be executed in the listener
 * @return an instance of [AnonymousListener]
 * @see register
 */
inline fun <T : Event> listener(crossinline predicate: Predicate<T> = { true }, crossinline action: AnonymousListener<T>.(T) -> Unit) = object : AnonymousListener<T> {
    override fun onEvent(event: T) {
        if (predicate(event)) action(event)
    }
}

/**
 * Registers an [AnonymousListener]. Parameters derived from [org.bukkit.event.EventHandler]
 *
 * @param plugin the plugin registering the listener
 * @param priority the listener priority
 * @param ignoreCancelled whether the listener should ignore cancelled events
 *
 * @see [com.zp4rker.bukkot.extensions.unregister]
 */
inline fun <reified T : Event> AnonymousListener<T>.register(
    plugin: Plugin,
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
) {
    @Suppress("UNCHECKED_CAST")
    val executor = EventExecutor { listener, event -> (listener as AnonymousListener<T>).onEvent(event as T) }
    PMANAGER.registerEvent(T::class.java, this, priority, executor, plugin, ignoreCancelled)
}