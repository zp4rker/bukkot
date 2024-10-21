package com.zp4rker.bukkot.storage

import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

/**
 * @author zp4rker
 */
class SpigotSettings(file: File) : YamlConfiguration() {
    init {
        load(file)
    }

    companion object {
        val INSTANCE: SpigotSettings get() = SpigotSettings(File("spigot.yml"))
    }
}