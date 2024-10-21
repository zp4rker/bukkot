package com.zp4rker.bukkot.builders.item

import org.bukkit.enchantments.Enchantment

/**
 * @author zp4rker
 */
class EnchantNode {
    val set = mutableSetOf<EnchantContainer>()

    fun with(enchantment: Enchantment) = EnchantContainer(enchantment).also { set.add(it) }

    inline fun with(vararg enchantments: Enchantment, conf: EnchantContainer.(Enchantment) -> Unit) {
        enchantments.forEach {
            with(it).apply { conf(it) }
        }
    }

    data class EnchantContainer internal constructor(val enchantment: Enchantment, var level: Int = 1) {
        infix fun level(level: Int) {
            this.level = level
        }
    }
}