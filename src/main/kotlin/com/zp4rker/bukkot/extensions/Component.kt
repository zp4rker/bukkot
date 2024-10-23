package com.zp4rker.bukkot.extensions

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.json.JSONComponentSerializer
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer

/**
 * Convert to legacy Minecraft string (i.e. "§3Test")
 */
fun Component.legacy() = LegacyComponentSerializer.legacySection().serialize(this)

/**
 * Convert to json string
 */
fun Component.json() = JSONComponentSerializer.json().serialize(this)

/**
 * Convert to plain text. All formatting stripped
 */
fun Component.plain() = PlainTextComponentSerializer.plainText().serialize(this)

/**
 * Convert to minimessage format (i.e. "<gold>Hello!</gold>")
 */
fun Component.minimessage() = MM.serialize(this)