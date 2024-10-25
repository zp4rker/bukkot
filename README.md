<div align="center">

![Logo](https://i.imgur.com/YnVO61v.png)

---

![Last commit](https://img.shields.io/github/last-commit/zp4rker/bukkot?style=flat)
![Latest tag](https://img.shields.io/github/v/tag/zp4rker/bukkot?label=current+version&style=flat)
![License](https://img.shields.io/github/license/zp4rker/bukkot?style=flat)
![Lines of code](https://img.shields.io/endpoint?url=https://ghloc.vercel.app/api/zp4rker/bukkot/badge?filter=.kt$,!sample)
[![Discord Badge](https://discordapp.com/api/guilds/647312158832721934/widget.png)](https://zp4rker.com/discord)

Kotlin meets Bukkit. Kotlin packaged into a plugin, as well as some added features using Kotlin.

</div>

# Download
![Maven Central Last Update](https://img.shields.io/maven-central/last-update/com.zp4rker/bukkot) ![Maven Central Version](https://img.shields.io/maven-central/v/com.zp4rker/bukkot)
## Maven

```xml
<dependency>
    <groupId>com.zp4rker</groupId>
    <artifactId>bukkot</artifactId>
    <version>2.1.0-k2.0.21</version>
</dependency>
```

## Gradle

```groovy
compile "com.zp4rker:bukkot:2.1.0-k2.0.21"
```

# Features

- Item(stack) builder
- New way(s) to register listeners
    - Plus register listeners on specific subjects/entities
- Ease of access to BukkitScheduler
- `TimeUnit.X.toTicks(...)` and `TickTimeUnit`
- Custom YAML files using `YamlFile`
- Various extension functions such as `String#colorify()`
- `spigot.yml` available as a `YamlConfiguration`
- As of `v1.1.0`, Kotlin reflect and coroutines are also included
