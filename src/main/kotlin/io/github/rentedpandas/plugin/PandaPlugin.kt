package io.github.rentedpandas.plugin

import org.bukkit.plugin.java.JavaPlugin

/**
 * This is the main instance of PandaLib.
 *
 * This class should be used instead of [JavaPlugin()]
 * as this class inherits from it.
 *
 * You should use these override functions instead:
 * - [load()]
 * - [startup()]
 * - [shutdown()]
 */
abstract class PandaPlugin : JavaPlugin() {

    /**
     * Called when the plugin loads.
     */
    open fun load() {}

    /**
     * Called when the plugin enables.
     */
    open fun startup() {}

    /**
     * Called when the plugin disables.
     */
    open fun shutdown() {}

    final override fun onLoad() {
        super.onLoad()
        load()
    }

    final override fun onEnable() {
        startup()
    }

    final override fun onDisable() {
        shutdown()
    }

}

lateinit var PANDAINSTANCE: PandaPlugin private set