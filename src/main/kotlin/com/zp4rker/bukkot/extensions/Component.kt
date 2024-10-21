package com.zp4rker.bukkot.extensions

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.json.JSONComponentSerializer
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer

/**
 * @author zp4rker
 */
fun Component.legacy() = LegacyComponentSerializer.legacySection().serialize(this)
fun Component.json() = JSONComponentSerializer.json().serialize(this)
fun Component.plain() = PlainTextComponentSerializer.plainText().serialize(this)
fun Component.minimessage() = MM.serialize(this)