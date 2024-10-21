package com.zp4rker.bukkot.listener

import org.bukkit.block.Block
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.event.EventPriority
import org.bukkit.event.block.BlockEvent
import org.bukkit.event.entity.EntityEvent
import org.bukkit.event.player.PlayerEvent
import org.bukkit.plugin.java.JavaPlugin

/**
 * @author zp4rker
 */
inline fun <reified T : EntityEvent> Entity.on(
    plugin: JavaPlugin,
    crossinline predicate: Predicate<T> = { true },
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
    crossinline action: AnonymousListener<T>.(T) -> Unit
) {
    plugin.on<T>({
        if (it.entity.uniqueId == this.uniqueId) predicate(it)
        else false
    }, priority, ignoreCancelled, action)
}

inline fun <reified T : EntityEvent> Entity.expect(
    plugin: JavaPlugin,
    crossinline predicate: Predicate<T> = { true },
    amount: Int = 1,
    timeout: Long = 0,
    crossinline timeoutAction: () -> Unit = {},
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
    crossinline action: AnonymousListener<T>.(T) -> Unit
) {
    plugin.expect<T>({
        if (it.entity.uniqueId == this.uniqueId) predicate(it)
        else false
    }, amount, timeout, timeoutAction, priority, ignoreCancelled, action)
}

inline fun <reified T : PlayerEvent> Player.on(
    plugin: JavaPlugin,
    crossinline predicate: Predicate<T> = { true },
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
    crossinline action: AnonymousListener<T>.(T) -> Unit
) {
    plugin.on<T>({
        if (it.player.uniqueId == this.uniqueId) predicate(it)
        else false
    }, priority, ignoreCancelled, action)
}

inline fun <reified T : PlayerEvent> Player.expect(
    plugin: JavaPlugin,
    crossinline predicate: Predicate<T> = { true },
    amount: Int = 1,
    timeout: Long = 0,
    crossinline timeoutAction: () -> Unit = {},
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
    crossinline action: AnonymousListener<T>.(T) -> Unit
) {
    plugin.expect<T>({
        if (it.player.uniqueId == this.uniqueId) predicate(it)
        else false
    }, amount, timeout, timeoutAction, priority, ignoreCancelled, action)
}

inline fun <reified T : BlockEvent> Block.on(
    plugin: JavaPlugin,
    crossinline predicate: Predicate<T> = { true },
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
    crossinline action: AnonymousListener<T>.(T) -> Unit
) {
    plugin.on<T>({
        if (it.block.location == this.location && it.block.blockData.asString == this.blockData.asString) predicate(it)
        else false
    }, priority, ignoreCancelled, action)
}

inline fun <reified T : BlockEvent> Block.expect(
    plugin: JavaPlugin,
    crossinline predicate: Predicate<T> = { true },
    amount: Int = 1,
    timeout: Long = 0,
    crossinline timeoutAction: () -> Unit = {},
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
    crossinline action: AnonymousListener<T>.(T) -> Unit
) {
    plugin.expect<T>({
        if (it.block.location == this.location && it.block.blockData.asString == this.blockData.asString) predicate(it)
        else false
    }, amount, timeout, timeoutAction, priority, ignoreCancelled, action)
}