package com.zp4rker.bukkot.extensions

import org.bukkit.ChatColor

/**
 * @author zp4rker
 */
fun String.colorify(char: Char = '&') = ChatColor.translateAlternateColorCodes(char, this)

fun String.stripColors() = ChatColor.stripColor(this)