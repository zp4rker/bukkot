@file:Suppress("deprecation")

package com.zp4rker.bukkot.extensions

import net.kyori.adventure.text.Component
import org.bukkit.ChatColor

/**
 * Colours a legacy Minecraft string (i.e. "&3Test")
 */
fun String.colourify(char: Char = '&') = ChatColor.translateAlternateColorCodes(char, this)

/**
 * For the Americans
 *
 * @see colourify
 */
fun String.colorify(char: Char = '&') = colourify(char)

/**
 * Strip colours from string
 */
fun String.stripColors() = ChatColor.stripColor(this)

/**
 * Convert plain text to [Component] for use in Paper
 */
fun String.component() = Component.text(this)

/**
 * Convert [net.kyori.adventure.text.minimessage.MiniMessage] format to [Component]
 */
fun String.minimessage() = MM.deserialize(this)