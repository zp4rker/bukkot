package com.zp4rker.bukkot.storage

import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

/**
 * @author zp4rker
 */
class YamlFile(private val plugin: JavaPlugin, name: String) : YamlConfiguration() {
    private val file: File

    init {
        val fileName = if (name.endsWith(".yml")) name else "$name.yml"
        file = File(plugin.dataFolder, fileName)

        val res = plugin.getResource(name)
        if (!file.exists()) {
            res?.let { load(it.reader()) }
        } else {
            load(file)
            res?.let { addDefaults(loadConfiguration(it.reader())) }
        }
    }

    fun save() = save(file)

    fun reload() = load(file)

    fun writeDefaultFile(override: Boolean = false) {
        plugin.saveResource(file.name, override)
    }
}