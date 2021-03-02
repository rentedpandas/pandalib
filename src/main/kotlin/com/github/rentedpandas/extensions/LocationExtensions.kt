package com.github.rentedpandas.extensions

import org.bukkit.Location
import org.bukkit.World
import org.bukkit.util.Vector

/**
 * Gets the world of a location.
 * @throws NullPointerException if world cannot be found.
 */

// Simple coordinates
val Location.worldEx: World get() = world ?: throw NullPointerException("World cannot be found.")

infix fun Location.increase(value: Double) = add(value, value, value)
infix fun Location.increaseX(x: Double) = add(x, 0.0, 0.0)
infix fun Location.increaseY(y: Double) = add(0.0, y, 0.0)
infix fun Location.increaseZ(z: Double) = add(0.0, 0.0, z)

infix fun Location.decrease(value: Double) = subtract(value, value, value)
infix fun Location.decreaseX(x: Double) = subtract(x, 0.0, 0.0)
infix fun Location.decreaseY(y: Double) = subtract(0.0, y, 0.0)
infix fun Location.decreaseZ(z: Double) = subtract(0.0, 0.0, z)

val Location.getLocationAtBlock: Location get() = Location(world, blockX.toDouble(), blockY.toDouble(), blockZ.toDouble())

// Operator

operator fun Location.plus(vector: Vector) = clone().add(vector)
operator fun Location.minus(vector: Vector) = clone().subtract(vector)
operator fun Location.plus(location: Location) = clone().add(location)
operator fun Location.minus(location: Location) = clone().subtract(location)