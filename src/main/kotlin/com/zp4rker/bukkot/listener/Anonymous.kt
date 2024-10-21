package com.zp4rker.bukkot.listener

import com.zp4rker.bukkot.extensions.manager
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.plugin.EventExecutor
import org.bukkit.plugin.Plugin

/**
 * @author zp4rker
 */
interface AnonymousListener<T : Event> : Listener {
    fun onEvent(event: T)
}

inline fun <T : Event> listener(crossinline action: AnonymousListener<T>.(T) -> Unit) = object : AnonymousListener<T> {
    override fun onEvent(event: T) = action(event)
}

inline fun <reified T : Event> AnonymousListener<T>.register(
    plugin: Plugin,
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
) {
    @Suppress("UNCHECKED_CAST")
    val executor = EventExecutor { listener, event -> (listener as AnonymousListener<T>).onEvent(event as T) }
    plugin.manager.registerEvent(T::class.java, this, priority, executor, plugin, ignoreCancelled)
}