plugins {
    kotlin("jvm") version "2.0.21"
    kotlin("plugin.serialization") version "2.0.21"

    id("com.gradleup.shadow") version "8.3.3"
}

group = "com.zp4rker"
version = "2.0.0-k2.0.21"
description = "Kotlin meets Bukkit. Kotlin packaged into a plugin, as well as some added features using Kotlin."

val mcVersion = "1.21.1"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/") // For Paper
    maven("https://repo.codemc.org/repository/maven-public") // For bStats
}

dependencies {
    compileOnly(kotlin("stdlib"))
    compileOnly(kotlin("reflect"))
    compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")

    compileOnly("io.papermc.paper:paper-api:$mcVersion-R0.1-SNAPSHOT")

    implementation("org.bstats:bstats-bukkit:1.7")
}

tasks {
    val filename = "${project.name}-${project.version}.jar"
    jar {
        archiveFileName = "original-$filename"
    }

    shadowJar {
        archiveFileName = filename
    }

    processResources {
        filesMatching("**plugin.yml") {
            expand(
                "version" to project.version,
                "description" to project.description,
                "mcVersion" to mcVersion
            )
        }
    }

    build {
        dependsOn(shadowJar)
    }
}