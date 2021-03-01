package io.github.rentedpandas.sound

import io.github.rentedpandas.extensions.applyIfNotNull
import io.github.rentedpandas.extensions.worldEx
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

    fun playLoc(location: Location) {
        val cat = category
        if (cat != null)
            location.worldEx.playSound(location, sound, cat, volume, pitch)
        else
            location.worldEx.playSound(location, sound, volume, pitch)
    }

    fun playPlayer(player: Player) {
        val cat = category
        if (cat != null)
            player.playSound(player.location, sound, cat, volume, pitch)
        else
            player.playSound(player.location, sound, volume, pitch)
    }
}

fun sound(sound: Sound, builder: PSound.() -> Unit) = PSound(sound).apply(builder)
fun Location.sound(sound: Sound, builder: (PSound.() -> Unit)? = null) =
    PSound(sound).applyIfNotNull(builder).playLoc(this)

fun Player.sound(sound: Sound, builder: (PSound.() -> Unit)? = null) =
    PSound(sound).applyIfNotNull(builder).playPlayer(this)