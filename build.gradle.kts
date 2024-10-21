plugins {
    kotlin("jvm") version "2.0.21"
    kotlin("plugin.serialization") version "2.0.21"
}

group = "com.zp4rker"
version = "2.0.0-k2.0.21"

val mcVersion = "1.21.1"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://repo.codemc.org/repository/maven-public")
}

dependencies {
    compileOnly(kotlin("stdlib"))
    compileOnly(kotlin("reflect"))
    compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:2.0.21")

    compileOnly("org.spigotmc:spigot-api:$mcVersion-R0.1-SNAPSHOT")

    compileOnly("org.bstats:bstats-bukkit:1.7")
}

tasks.processResources {
    filesMatching("**plugin.yml") {
        expand("version" to project.version)
    }
}