@file:Suppress("deprecation")
package com.zp4rker.bukkot.extensions

import net.kyori.adventure.text.Component
import org.bukkit.ChatColor

/**
 * @author zp4rker
 */
fun String.colorify(char: Char = '&') = ChatColor.translateAlternateColorCodes(char, this)
fun String.stripColors() = ChatColor.stripColor(this)

fun String.component() = Component.text(this)
fun String.minimessage() = MM.deserialize(this)