plugins {
    kotlin("jvm") version "1.5.0"
    kotlin("plugin.serialization") version "1.5.0"

    id("com.github.johnrengelman.shadow") version "6.1.0"

    signing
    maven

    id("io.codearte.nexus-staging") version "0.22.0"
}

group = "com.zp4rker"
version = "1.1.0-k1.5.0"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://repo.codemc.org/repository/maven-public")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0-RC")
    implementation("org.bstats:bstats-bukkit:1.7")
    shadow("org.spigotmc:spigot-api:1.16.4-R0.1-SNAPSHOT")
}

tasks.shadowJar {
    archiveClassifier.set("")

    relocate("org.bstats", "com.zp4rker.bukkit.bstats")
}

tasks.processResources {
    filesMatching("**plugin.yml") {
        expand("version" to project.version)
    }
}

tasks.build {
    dependsOn("shadowJar")
    dependsOn("processResources")
}

tasks.create<Jar>("javadocJar") {
    archiveClassifier.set("javadoc")
    from(tasks.getByName("javadoc"))
}

tasks.create<Jar>("sourcesJar") {
    archiveClassifier.set("sources")
    from(sourceSets["main"].allSource)
}

artifacts {
    add("archives", tasks.getByName("javadocJar"))
    add("archives", tasks.getByName("sourcesJar"))
}

signing {
    sign(configurations["archives"])
}

tasks.named<Upload>("uploadArchives") {
    repositories {
        withConvention(MavenRepositoryHandlerConvention::class) {
            mavenDeployer {
                beforeDeployment {
                    signing.signPom(this)
                }

                withGroovyBuilder {
                    "repository"("url" to "https://oss.sonatype.org/service/local/staging/deploy/maven2/") {
                        "authentication"("userName" to properties["ossrhUsername"], "password" to properties["ossrhPassword"])
                    }
                    "snapshotRepository"("url" to "https://oss.sonatype.org/content/repositories/snapshots/") {
                        "authentication"("userName" to properties["ossrhUsername"], "password" to properties["ossrhPassword"])
                    }
                }

                pom.project {
                    withGroovyBuilder {
                        "name"("Bukkot")
                        "artifactId"("bukkot")
                        "packaging"("jar")
                        "description"("Kotlin meets Bukkit. Kotlin packaged into a plugin, as well as some added features using Kotlin.")
                        "url"("https://github.com/zp4rker/bukkot")

                        "scm" {
                            "connection"("scm:git:git://github.com/zp4rker/bukkot.git")
                            "url"("https://github.com/zp4rker/bukkot")
                        }

                        "licenses" {
                            "license" {
                                "name"("The Apache License, Version 2.0")
                                "url"("http://www.apache.org/licenses/LICENSE-2.0.txt")
                            }
                        }

                        "developers" {
                            "developer" {
                                "id"("zp4rker")
                                "name"("Zaeem Parker")
                                "email"("iam@zp4rker.com")
                            }
                        }
                    }
                }
            }
        }
    }
}