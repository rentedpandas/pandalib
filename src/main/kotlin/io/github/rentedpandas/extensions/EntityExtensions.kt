package io.github.rentedpandas.extensions

import io.github.rentedpandas.plugin.PANDAINSTANCE
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Damageable
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.inventory.EquipmentSlot

/**
 * Kills the damageable.
 */
fun Damageable.kill() { health = 0.0 }

/**
 * Sets the health of a living entity to its max value.
 * @throws NullPointerException if [Attribute.GENERIC_MAX_HEALTH] is null.
 */
fun LivingEntity.heal() {
    health = getAttribute(Attribute.GENERIC_MAX_HEALTH)?.value
        ?: throw NullPointerException("Attribute GENERIC_MAX_HEALTH is null.")
}

/**
 * Sets the food level and the saturation level of a
 * player to the max value.
 */
fun Player.sate() {
    foodLevel = 20
    saturation = 20F
}

/**
 * Vanishes the player from all other players.
 */
fun Player.vanish() {
    onlinePlayers.filter { it != this }.forEach { it.hidePlayer(PANDAINSTANCE, this) }
}

/**
 * Reveals the player to all other players.
 */
fun Player.unvanish() {
    onlinePlayers.filter { it != this }.forEach { it.showPlayer(PANDAINSTANCE, this) }
}

/**
 * Hides all online players from the player.
 */
fun Player.hideOnlinePlayers() {
    onlinePlayers.filter { it != this }.forEach { this.hidePlayer(PANDAINSTANCE, it) }
}

/**
 * Shows all online players to the player.
 */
fun Player.showOnlinePlayers() {
    onlinePlayers.filter { it != this }.forEach { this.showPlayer(PANDAINSTANCE, it) }
}

/**
 * Get item in either player's main or off hand.
 */
fun Player.getItem(slot: EquipmentSlot?) = when (slot) {
    EquipmentSlot.HAND -> inventory.itemInMainHand
    EquipmentSlot.OFF_HAND -> inventory.itemInOffHand
    else -> null
}