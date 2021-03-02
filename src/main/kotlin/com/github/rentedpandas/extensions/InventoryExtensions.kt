package com.github.rentedpandas.extensions

import org.bukkit.entity.Player
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack

/**
 * Used to get the item that a player used to interact
 * with another entity.
 */
val PlayerInteractEntityEvent.interactionItem: ItemStack?
    get() {
        val player: Player = this.player
        return when (this.hand) {
            EquipmentSlot.HAND -> player.inventory.itemInMainHand
            EquipmentSlot.OFF_HAND -> player.inventory.itemInOffHand
            EquipmentSlot.CHEST, EquipmentSlot.FEET, EquipmentSlot.HEAD, EquipmentSlot.LEGS -> null
        }
    }

/**
 * @return True if a left-click was initiated.
 */
val Action.left get() = this == Action.LEFT_CLICK_BLOCK || this == Action.LEFT_CLICK_AIR

/**
 * @return True if a right-click was initiated.
 */
val Action.right get() = this == Action.RIGHT_CLICK_BLOCK || this == Action.RIGHT_CLICK_AIR