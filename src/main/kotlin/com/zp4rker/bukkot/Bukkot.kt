package com.zp4rker.bukkot

import org.bstats.bukkit.Metrics
import org.bukkit.plugin.java.JavaPlugin

/**
 * @author zp4rker
 */
class Bukkot : JavaPlugin() {

    override fun onEnable() {
        initMetrics().let {
            if (it.isEnabled) logger.info("Metrics is enabled.")
        }
    }

    private fun initMetrics() = Metrics(this, 9605)

}