package com.zp4rker.bukkot.builders.item

import com.google.common.collect.ArrayListMultimap
import com.zp4rker.bukkot.extensions.MM
import com.zp4rker.bukkot.extensions.minimessage
import com.zp4rker.bukkot.extensions.plain
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

/**
 * Creates a customised [ItemStack]
 *
 * @param type material of the itemstack
 * @param data customisations of itemstack
 */
fun item(type: Material, data: ItemStack.() -> Unit) = ItemStack(type).apply(data)

/**
 * Customises an [ItemStack]
 *
 * @param base the itemstack to start off with
 * @see item
 */
fun item(base: ItemStack, data: ItemStack.() -> Unit) = ItemStack(base).apply(data)

/**
 * Apply changes to [ItemMeta]
 *
 * @param data the meta to apply
 */
fun <T : ItemMeta> ItemStack.meta(data: T.() -> Unit) {
    val meta = itemMeta(type, data)
    itemMeta = meta
}

/**
 * Customised [ItemMeta] for type
 *
 * @param type material to generate meta for
 * @param data the meta to apply
 */
@Suppress("UNCHECKED_CAST")
fun <T : ItemMeta> itemMeta(type: Material, data: T.() -> Unit) = Bukkit.getItemFactory().getItemMeta(type)
    .let { it as? T }
    ?.apply(data)
    ?: throw IllegalArgumentException("Invalid ItemMeta for specified material.")

/**
 * [ItemMeta] lore lines as combined string in [net.kyori.adventure.text.minimessage.MiniMessage] format
 */
var ItemMeta.loreString: String?
    get() = lore()?.joinToString("\n") { it.minimessage() }
    set(value) {
        value?.let {
            lore(it.split("\n").map { str -> MM.deserialize(str) })
        }
    }

/**
 * [ItemMeta] lore lines in plain text
 */
val ItemMeta.plainLore: List<String>?
    get() = lore()?.map { it.plain() }

/**
 * [ItemMeta] lore lines in plain text as combined string
 */
val ItemMeta.plainLoreString: String?
    get() = plainLore?.joinToString("\n")

var ItemMeta.name: Component?
    get() = if (hasDisplayName()) displayName() else null
    set(value) = displayName(value)

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

/**
 * Add [ItemAttributes] to [ItemMeta]
 *
 * @param data the attributes to apply
 */
fun ItemMeta.attributes(data: ItemAttributes.() -> Unit) {
    val attributes = ItemAttributes().apply(data)
    val mods = attributes.modifiers
    attributeModifiers =
        ArrayListMultimap.create(if (attributeModifiers == null) ArrayListMultimap.create() else attributeModifiers!!)
            .also {
                it.putAll(mods)
            }
}

/**
 * Add enchants to [ItemMeta]
 *
 * @param unsafe bypass level restriction
 * @param data the enchants to apply
 */
fun ItemMeta.enchant(unsafe: Boolean = false, data: EnchantNode.() -> Unit) {
    EnchantNode().apply(data).set.forEach {
        addEnchant(it.enchantment, it.level, unsafe)
    }
}