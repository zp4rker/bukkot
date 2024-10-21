package com.zp4rker.bukkot.yaml

import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

/**
 * @author zp4rker
 */
object SpigotSettings : YamlConfiguration() {
    init {
        load(File("spigot.yml"))
    }
}