package com.github.rentedpandas.extensions

import com.github.rentedpandas.plugin.PANDAINSTANCE
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

/**
 * Get all online players.
 * @see Bukkit.getOnlinePlayers
 */
val onlinePlayers: Collection<Player> get() = Bukkit.getOnlinePlayers()

/**
 * Get console command sender.
 * @see Bukkit.getConsoleSender
 */
val console get() = Bukkit.getConsoleSender()

/**
 * Get all online players AND console.
 */
val everyone: Collection<CommandSender> get() = Bukkit.getOnlinePlayers().plus(Bukkit.getConsoleSender())

/**
 * Get server.
 * @see Bukkit.getServer
 */
val server get() = Bukkit.getServer()

/**
 * Get plugin manager.
 * @see Bukkit.getPluginManager
 */
val pluginManager get() = Bukkit.getPluginManager()

/**
 * Get plugin instance data folder.
 */
val pluginDataFolder get() = PANDAINSTANCE.dataFolder