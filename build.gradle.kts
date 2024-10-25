plugins {
    kotlin("jvm") version "2.0.21"
    kotlin("plugin.serialization") version "2.0.21"
    id("org.jetbrains.dokka") version "1.9.20"

    id("com.gradleup.shadow") version "8.3.3"

    `maven-publish`
    signing

    id("xyz.jpenilla.run-paper") version "2.3.1"
}

group = "com.zp4rker"
version = "2.1.0-k2.0.21"
description = "Kotlin meets Bukkit. Kotlin packaged into a plugin, as well as some added features using Kotlin"

val mcVersion = "1.21.1"
project.ext["mcVersion"] = mcVersion

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/") // For Paper
    maven("https://repo.codemc.org/repository/maven-public") // For bStats
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")

    implementation("io.papermc.paper:paper-api:$mcVersion-R0.1-SNAPSHOT")

    implementation("org.bstats:bstats-bukkit:1.7")
}

tasks {
    jar {
        archiveClassifier = "original"
    }

    shadowJar {
        archiveClassifier = ""
        relocate("org.bstats", "com.zp4rker.bukkot.bstats")
        dependencies {
            include { it.moduleGroup == "org.bstats" }
        }
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

    create<Jar>("javadocJar") {
        archiveClassifier = "javadoc"
        from(javadoc)
    }
    create<Jar>("sourcesJar") {
        archiveClassifier = "sources"
        from(sourceSets["main"].allSource)
    }
}

val sources = artifacts.add("archives", tasks["sourcesJar"])
val javadocs = artifacts.add("archives", tasks["javadocJar"])
val final = artifacts.add("archives", tasks["shadowJar"])

publishing {
    publications {
        create<MavenPublication>("bukkot") {
            artifactId = project.name
            from(components["kotlin"])

            artifact(sources)
            artifact(javadocs)
            artifact(final)

            pom {
                name = project.name
                description = project.description
                url = "https://github.com/zp4rker/bukkot"

                packaging = "jar"

                licenses {
                    license {
                        name = "The Apache License, Version 2.0"
                        url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
                    }
                }

                developers {
                    developer {
                        id = "zp4rker"
                        name = "Zaeem Parker"
                        email = "iam@zp4rker.com"
                    }
                }

                scm {
                    connection = "scm:git:git://github.com/zp4rker/bukkot.git"
                    url = "https://github.com/zp4rker/bukkot"
                }

                repositories {
                    maven {
                        val stagingUrl = "https://oss.sonatype.org/service/local/staging/deploy/maven2"
                        val snapshotsUrl = "https://oss.sonatype.org/content/repositories/snapshots"
                        url = uri(if (project.version.toString().endsWith("-SNAPSHOT")) snapshotsUrl else stagingUrl)
                        credentials {
                            username = project.property("ossrhUsername").toString()
                            password = project.property("ossrhPassword").toString()
                        }
                    }
                }
            }
        }
    }
}

signing {
    useGpgCmd()
    sign(publishing.publications["bukkot"])
}

tasks.runServer {
    minecraftVersion(mcVersion)
    subprojects.forEach {
        pluginJars(it.tasks.jar.map { jar -> jar.archiveFile })
    }
}