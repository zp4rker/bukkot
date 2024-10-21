package com.zp4rker.bukkot.extensions

import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.Server
import org.bukkit.plugin.PluginManager

/**
 * @author zp4rker
 */
object PMANAGER : PluginManager by Bukkit.getPluginManager()
object SERVER : Server by Bukkit.getServer()

val MM = MiniMessage.miniMessage()