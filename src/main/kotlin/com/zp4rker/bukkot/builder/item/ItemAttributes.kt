package com.zp4rker.bukkot.builder.item

import com.google.common.collect.ArrayListMultimap
import com.google.common.collect.Multimap
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier

/**
 * @author zp4rker
 */
class ItemAttributes {

    val modifiers: Multimap<Attribute, AttributeModifier> = ArrayListMultimap.create()

    fun modify(attribute: Attribute) = ModNode(attribute)

    inner class ModNode internal constructor(private val attribute: Attribute) {

        infix fun with(modifier: AttributeModifier) {
            modifiers.put(attribute, modifier)
        }

        infix fun with(modifiers: Iterable<AttributeModifier>) {
            this@ItemAttributes.modifiers.putAll(attribute, modifiers)
        }

    }

}