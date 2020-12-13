package com.zp4rker.bukkot.extension

import org.bukkit.Bukkit
import org.bukkit.Server
import org.bukkit.plugin.PluginManager

/**
 * @author zp4rker
 */

val PLUGIN_MANAGER: PluginManager get() = Bukkit.getPluginManager()
val SERVER: Server get() = Bukkit.getServer()