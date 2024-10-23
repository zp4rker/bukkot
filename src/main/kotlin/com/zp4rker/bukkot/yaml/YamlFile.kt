package com.zp4rker.bukkot.yaml

import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

/**
 * Access [YamlConfiguration] for any file located in plugin's data folder
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

    /**
     * Saves the config to file
     */
    fun save() = save(file)

    /**
     * Reloads the config from file
     */
    fun reload() = load(file)

    /**
     * Saves the content from the associated resource file to actual file
     *
     * @param overwrite whether to overwrite the file or not
     */
    fun writeDefaultFile(overwrite: Boolean = false) {
        plugin.saveResource(file.name, overwrite)
    }
}