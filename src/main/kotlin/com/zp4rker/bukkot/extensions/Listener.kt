package com.zp4rker.bukkot.extensions

import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

/**
 * @author zp4rker
 */
fun Listener.register(plugin: JavaPlugin) {
    plugin.manager.registerEvents(this, plugin)
}

fun Listener.unregister() {
    HandlerList.unregisterAll(this)
}