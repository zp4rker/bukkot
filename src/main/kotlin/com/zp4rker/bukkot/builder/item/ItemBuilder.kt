package com.zp4rker.bukkot.builder.item

import com.google.common.collect.ArrayListMultimap
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

/**
 * @author zp4rker
 */

fun item(type: Material, data: ItemStack.() -> Unit) = ItemStack(type).apply(data)

fun item(base: ItemStack, data: ItemStack.() -> Unit) = ItemStack(base).apply(data)

fun <T : ItemMeta> ItemStack.meta(data: T.() -> Unit) {
    val meta = itemMeta(type, data)
    itemMeta = meta
}

@Suppress("UNCHECKED_CAST")
fun <T : ItemMeta> itemMeta(type: Material, data: T.() -> Unit) = Bukkit.getItemFactory().getItemMeta(type)
    .let { it as? T }
    ?.apply(data)
    ?: throw IllegalArgumentException("Invalid ItemMeta for specified material.")

var ItemMeta.fullLore: String?
    get() = lore?.joinToString("\n")
    set(value) {
        lore = value?.split("\n")
    }

var ItemMeta.name: String?
    get() = if (hasDisplayName()) displayName else null
    set(value) {
        setDisplayName(value)
    }

var ItemMeta.modelData: Int?
    get() = if (hasCustomModelData()) customModelData else null
    set(value) {
        setCustomModelData(value)
    }

var ItemMeta.unbreakable: Boolean
    get() = isUnbreakable
    set(value) {
        isUnbreakable = value
    }

fun ItemMeta.flag(vararg flags: ItemFlag) = addItemFlags(*flags)

fun ItemMeta.attributes(data: ItemAttributes.() -> Unit) {
    val attributes = ItemAttributes().apply(data)
    val mods = attributes.modifiers
    attributeModifiers = ArrayListMultimap.create(if (attributeModifiers == null) ArrayListMultimap.create() else attributeModifiers!!).also {
        it.putAll(mods)
    }
}

fun ItemMeta.enchant(unsafe: Boolean = false, data: EnchantNode.() -> Unit) {
    EnchantNode().apply(data).set.forEach {
        addEnchant(it.enchantment, it.level, unsafe)
    }
}