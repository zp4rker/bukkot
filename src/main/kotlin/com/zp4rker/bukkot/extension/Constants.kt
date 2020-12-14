package com.zp4rker.bukkot.extension

import org.bukkit.Bukkit
import org.bukkit.Server
import org.bukkit.plugin.PluginManager

/**
 * @author zp4rker
 */

object PLUGIN_MANAGER: PluginManager by Bukkit.getPluginManager()
object SERVER: Server by Bukkit.getServer()