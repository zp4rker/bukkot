plugins {
    kotlin("jvm") version "2.0.21"
}

group = "com.zp4rker"
version = rootProject.version
description = "A sample plugin using Bukkot"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/") // For Paper
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:${rootProject.property("mcVersion")}-R0.1-SNAPSHOT")

    compileOnly(rootProject)
}

tasks {
    jar {
        archiveFileName = "${rootProject.name}-${project.name}-${project.version}.jar"
    }

    processResources {
        filesMatching("**plugin.yml") {
            expand(
                "version" to project.version,
                "description" to project.description,
                "mcVersion" to rootProject.property("mcVersion")
            )
        }
    }
}