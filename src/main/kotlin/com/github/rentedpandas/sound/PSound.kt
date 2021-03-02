package com.github.rentedpandas.sound

import com.github.rentedpandas.extensions.applyIfNotNull
import com.github.rentedpandas.extensions.worldEx
import org.bukkit.Location
import org.bukkit.Sound
import org.bukkit.SoundCategory
import org.bukkit.entity.Player

data class PSound(
    val sound: Sound,
    var volume: Float = 1f,
    var pitch: Float = 1f,
    var category: SoundCategory? = null
) {
    /**
     * Plays a sound at a given location.
     * @param location Location of where the sound should be played.
     */
    fun playLoc(location: Location) {
        val cat = category
        if (cat != null)
            location.worldEx.playSound(location, sound, cat, volume, pitch)
        else
            location.worldEx.playSound(location, sound, volume, pitch)
    }

    /**
     * Plays a sound at a player's location.
     * @param player Get the online player's location and play a sound.
     */
    fun playPlayer(player: Player) {
        val cat = category
        if (cat != null)
            player.playSound(player.location, sound, cat, volume, pitch)
        else
            player.playSound(player.location, sound, volume, pitch)
    }
}

/**
 * Sound builder tool
 */
fun sound(sound: Sound, builder: PSound.() -> Unit) = PSound(sound).apply(builder)

/**
 * @see sound
 */
fun Location.sound(sound: Sound, builder: (PSound.() -> Unit)? = null) =
    PSound(sound).applyIfNotNull(builder).playLoc(this)

/**
 * @see sound
 */
fun Player.sound(sound: Sound, builder: (PSound.() -> Unit)? = null) =
    PSound(sound).applyIfNotNull(builder).playPlayer(this)