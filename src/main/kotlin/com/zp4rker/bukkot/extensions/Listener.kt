package com.zp4rker.bukkot.extensions

import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

/**
 * Registers listener by plugin
 *
 * @param plugin the plugin to register the listener
 */
fun Listener.register(plugin: JavaPlugin) {
    PMANAGER.registerEvents(this, plugin)
}

/**
 * Unregisters a listener
 */
fun Listener.unregister() {
    HandlerList.unregisterAll(this)
}