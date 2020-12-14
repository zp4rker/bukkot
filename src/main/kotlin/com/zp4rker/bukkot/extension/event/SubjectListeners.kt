package com.zp4rker.bukkot.extension.event

import org.bukkit.event.Event
import org.bukkit.event.block.*
import org.bukkit.event.enchantment.EnchantItemEvent
import org.bukkit.event.enchantment.PrepareItemEnchantEvent
import org.bukkit.event.entity.*
import org.bukkit.event.hanging.HangingBreakByEntityEvent
import org.bukkit.event.hanging.HangingEvent
import org.bukkit.event.hanging.HangingPlaceEvent
import org.bukkit.event.inventory.*
import org.bukkit.event.player.*
import org.bukkit.event.server.BroadcastMessageEvent
import org.bukkit.event.server.PluginEvent
import org.bukkit.event.server.ServerCommandEvent
import org.bukkit.event.server.TabCompleteEvent
import org.bukkit.event.vehicle.*
import org.bukkit.event.weather.WeatherEvent

/**
 * @author zp4rker
 */

object SubjectRegistry {


    private data class Entry<T: Event>(val type: Class<T>, val subject: (T) -> Set<Any?>)
    private val registry: MutableSet<Entry<out Event>> = mutableSetOf()

    init {
        /* Player Events */
        add(PlayerArmorStandManipulateEvent::class.java) { setOf(it.armorStandItem, it.playerItem) }
        add(PlayerBucketEvent::class.java) { setOf(it.itemStack) }
        add(PlayerChangedMainHandEvent::class.java) { setOf(it.player.inventory.itemInMainHand) }
        add(PlayerChangedWorldEvent::class.java) { setOf(it.from, it.player.world) }
        add(PlayerDropItemEvent::class.java) { setOf(it.itemDrop.itemStack) }
        add(PlayerEvent::class.java) { setOf(it.player) }
        add(PlayerHarvestBlockEvent::class.java) { setOf(it.harvestedBlock) }
        add(PlayerInteractEntityEvent::class.java) { setOf(it.rightClicked) }
        add(PlayerInteractEvent::class.java) { setOf(it.clickedBlock, it.item) }
        add(PlayerItemBreakEvent::class.java) { setOf(it.brokenItem) }
        add(PlayerItemConsumeEvent::class.java) { setOf(it.item) }
        add(PlayerItemDamageEvent::class.java) { setOf(it.item) }
        add(PlayerItemHeldEvent::class.java) { setOf(it.player.inventory.getItem(it.newSlot)) }
        add(PlayerItemMendEvent::class.java) { setOf(it.item) }
        add(PlayerLeashEntityEvent::class.java) { setOf(it.entity, it.player) }
        add(PlayerPickupArrowEvent::class.java) { setOf(it.item, it.arrow.shooter, it.arrow.attachedBlock) }
        add(PlayerShearEntityEvent::class.java) { setOf(it.entity) }
        add(PlayerSwapHandItemsEvent::class.java) { setOf(it.mainHandItem, it.offHandItem) }
        add(PlayerTakeLecternBookEvent::class.java) { setOf(it.book) }
        add(PlayerUnleashEntityEvent::class.java) { setOf(it.player) }

        /* Entity Events */
        add(EntityBreedEvent::class.java) { setOf(it.bredWith, it.breeder, it.mother, it.father) }
        add(EntityChangeBlockEvent::class.java) { setOf(it.block) }
        add(EntityCombustByBlockEvent::class.java) { setOf(it.combuster) }
        add(EntityCombustByEntityEvent::class.java) { setOf(it.combuster) }
        add(EntityDamageByBlockEvent::class.java) { setOf(it.damager) }
        add(EntityDamageByEntityEvent::class.java) { setOf(it.damager) }
        add(EntityDropItemEvent::class.java) { setOf(it.itemDrop.itemStack) }
        add(EntityEnterBlockEvent::class.java) { setOf(it.block) }
        add(EntityEnterLoveModeEvent::class.java) { setOf(it.humanEntity) }
        add(EntityEvent::class.java) { setOf(it.entity) }
        add(EntityInteractEvent::class.java) { setOf(it.block) }
        add(EntityPickupItemEvent::class.java) { setOf(it.item.itemStack) }
        add(EntityTameEvent::class.java) { setOf(it.owner) }
        add(EntityTargetEvent::class.java) { setOf(it.target) }
        add(EntityTransformEvent::class.java) { setOf(it.transformedEntity) }
        add(FoodLevelChangeEvent::class.java) { setOf(it.item) }
        add(ItemMergeEvent::class.java) { setOf(it.target.itemStack) }
        add(PigZombieAngerEvent::class.java) { setOf(it.target) }
        add(ProjectileHitEvent::class.java) { setOf(it.hitBlock, it.entity.shooter) }
        add(ProjectileLaunchEvent::class.java) { setOf(it.entity.shooter) }
        add(SpawnerSpawnEvent::class.java) { setOf(it.spawner.block) }
        add(HangingBreakByEntityEvent::class.java) { setOf(it.remover) }
        add(HangingEvent::class.java) { setOf(it.entity) }
        add(HangingPlaceEvent::class.java) { setOf(it.block, it.player) }
        add(VehicleBlockCollisionEvent::class.java) { setOf(it.block) }
        add(VehicleDamageEvent::class.java) { setOf(it.attacker) }
        add(VehicleDestroyEvent::class.java) { setOf(it.attacker) }
        add(VehicleEnterEvent::class.java) { setOf(it.entered) }
        add(VehicleEntityCollisionEvent::class.java) { setOf(it.entity) }
        add(VehicleEvent::class.java) { setOf(it.vehicle) + it.vehicle.passengers }
        add(VehicleExitEvent::class.java) { setOf(it.exited) }

        /* Block Events */
        add(BlockBreakEvent::class.java) { setOf(it.player) }
        add(BlockBurnEvent::class.java) { setOf(it.ignitingBlock) }
        add(BlockCanBuildEvent::class.java) { setOf(it.player) }
        add(BlockCookEvent::class.java) { setOf(it.result, it.source) }
        add(BlockDamageEvent::class.java) { setOf(it.player) }
        add(BlockDispenseArmorEvent::class.java) { setOf(it.targetEntity) }
        add(BlockDispenseEvent::class.java) { setOf(it.item) }
        add(BlockDropItemEvent::class.java) { setOf(it.player) + it.items }
        add(BlockEvent::class.java) { setOf(it.block) }
        add(BlockExplodeEvent::class.java) { it.blockList().toSet() }
        add(BlockFertilizeEvent::class.java) { setOf(it.player) + it.blocks }
        add(BlockFromToEvent::class.java) { setOf(it.toBlock) }
        add(BlockIgniteEvent::class.java) { setOf(it.ignitingBlock, it.ignitingEntity, it.player) }
        add(BlockPhysicsEvent::class.java) { setOf(it.sourceBlock) }
        add(BlockPistonExtendEvent::class.java) { it.blocks.toSet() }
        add(BlockPistonRetractEvent::class.java) { it.blocks.toSet() }
        add(BlockPlaceEvent::class.java) { setOf(it.blockAgainst, it.itemInHand, it.player) }
        add(BlockShearEntityEvent::class.java) { setOf(it.entity, it.tool) }
        add(BlockSpreadEvent::class.java) { setOf(it.source) }
        add(BrewEvent::class.java) { setOf(it.contents.fuel, it.contents.ingredient) }
        add(BrewingStandFuelEvent::class.java) { setOf(it.fuel) }
        add(CauldronLevelChangeEvent::class.java) { setOf(it.entity) }
        add(CraftItemEvent::class.java) { setOf(it.inventory.result) + it.inventory.matrix }
        add(FurnaceBurnEvent::class.java) { setOf(it.fuel) }
        add(FurnaceExtractEvent::class.java) { setOf(it.player) }
        add(SignChangeEvent::class.java) { setOf(it.player) }
        add(SpongeAbsorbEvent::class.java) { it.blocks.toSet() }

        /* Inventory-related Events */
        add(EnchantItemEvent::class.java) { setOf(it.enchantBlock, it.enchanter, it.item) }
        add(InventoryClickEvent::class.java) { setOf(it.currentItem, it.cursor) }
        add(InventoryCloseEvent::class.java) { setOf(it.player) }
        add(InventoryDragEvent::class.java) { setOf(it.oldCursor) + it.inventorySlots + it.newItems.values }
        add(InventoryEvent::class.java) { it.viewers.toSet() + it.inventory }
        add(InventoryInteractEvent::class.java) { setOf(it.whoClicked) }
        add(InventoryMoveItemEvent::class.java) { setOf(it.item) }
        add(InventoryOpenEvent::class.java) { setOf(it.player) }
        add(InventoryPickupItemEvent::class.java) { setOf(it.item, it.inventory) }
        add(PrepareAnvilEvent::class.java) { setOf(it.result) }
        add(PrepareItemCraftEvent::class.java) { setOf(it.inventory.result) + it.inventory.matrix }
        add(PrepareItemEnchantEvent::class.java) { setOf(it.enchantBlock, it.enchanter, it.item) }
        add(PrepareSmithingEvent::class.java) { setOf(it.result) }
        add(TradeSelectEvent::class.java) { setOf(it.merchant, it.inventory.selectedRecipe?.result) + it.inventory.selectedRecipe?.ingredients }

        /* Raid Events */
        /* not implementing for now */

        /* World Events */
        add(WeatherEvent::class.java) { setOf(it.world) }

        /* Server Events */
        add(BroadcastMessageEvent::class.java) { it.recipients.toSet() }
        add(PluginEvent::class.java) { setOf(it.plugin) }
        add(ServerCommandEvent::class.java) { setOf(it.sender) }
        add(TabCompleteEvent::class.java) { setOf(it.sender) }
    }

    private fun <T: Event> add(type: Class<T>, subject: (T) -> Set<Any?>) = registry.add(Entry(type, subject))
}