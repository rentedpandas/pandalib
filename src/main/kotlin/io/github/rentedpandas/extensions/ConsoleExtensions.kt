package io.github.rentedpandas.extensions

import io.github.rentedpandas.plugin.PANDAINSTANCE
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.plugin.Plugin

/**
 * Sends a message to console and details severity
 * of the message
 */
fun CommandSender.printDetailed(text: String, color: ChatColor, prefix: String, prefixColor: ChatColor) {
    sendMessage("$prefixColor${ChatColor.BOLD}$prefix  $color$text")
}

/** @see CommandSender.printDetailed */
fun CommandSender.println(text: String, plugin: Plugin? = PANDAINSTANCE) {
    printDetailed(text, ChatColor.WHITE, plugin?.name ?: "INFO", ChatColor.WHITE)
}

/** @see CommandSender.printDetailed */
fun CommandSender.nominal(text: String, plugin: Plugin? = PANDAINSTANCE) {
    printDetailed(text, ChatColor.YELLOW, plugin?.name?.plus(" - NOMINAL") ?: "NOMINAL", ChatColor.GREEN)
}

/** @see CommandSender.printDetailed */
fun CommandSender.warn(text: String, plugin: Plugin? = PANDAINSTANCE) {
    printDetailed(text, ChatColor.YELLOW, plugin?.name?.plus(" - WARN") ?: "WARN", ChatColor.GOLD)
}

/** @see CommandSender.printDetailed */
fun CommandSender.error(text: String, plugin: Plugin? = PANDAINSTANCE) {
    printDetailed(text, ChatColor.RED, plugin?.name?.plus(" - ERROR") ?: "ERROR", ChatColor.DARK_RED)
}