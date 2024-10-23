package com.zp4rker.bukkot.yaml

import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

/**
 * [YamlConfiguration] instance for the spigot settings (spigot.yml)
 */
object SpigotSettings : YamlConfiguration() {
    init {
        load(File("spigot.yml"))
    }
}