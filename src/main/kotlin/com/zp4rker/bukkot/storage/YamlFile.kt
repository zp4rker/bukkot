package com.zp4rker.bukkot.storage

import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

/**
 * @author zp4rker
 */
class YamlFile(plugin: JavaPlugin, name: String) : YamlConfiguration() {
    private val file: File

    init {
        val fileName = if (name.endsWith(".yml")) name else "$name.yml"
        file = File(plugin.dataFolder, fileName)

        if (!file.parentFile.exists()) file.parentFile.mkdirs()
        if (file.length() < 1) file.createNewFile()
        else load(file)
    }

    fun save() = save(file)

    fun reload() = load(file)

    fun copyDefaults(override: Boolean = false) {
        if (!file.parentFile.exists()) file.parentFile.mkdirs()
        if (file.length() < 1) {
            file.createNewFile()
            file.writer().use { saveToString() }
        } else {
            if (override) file.writer().use { saveToString() }
        }
    }

}