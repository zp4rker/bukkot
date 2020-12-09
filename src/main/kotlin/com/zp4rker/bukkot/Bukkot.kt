package com.zp4rker.bukkot

import org.bstats.bukkit.Metrics
import org.bukkit.plugin.java.JavaPlugin

/**
 * @author zp4rker
 */
class Bukkot : JavaPlugin() {

    override fun onEnable() {
        initMetrics()
    }

    private fun initMetrics() = Metrics(this, 9605)

}