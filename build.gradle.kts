@file:Suppress("VulnerableLibrariesLocal")

import net.minecrell.pluginyml.paper.PaperPluginDescription

plugins {
    kotlin("jvm") version "2.0.0"
    id("net.minecrell.plugin-yml.paper") version "0.6.0"
}

repositories {
    mavenCentral()
    // nbt-item-api
    maven("https://repo.codemc.io/repository/maven-public")
    // paper
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://oss.sonatype.org/content/groups/public/")
    // invui
    maven("https://repo.xenondevs.xyz/releases")
    // litecommands
    maven("https://repo.panda-lang.org/releases")
    // xz
    maven("https://jitpack.io")
    // worldguard
    maven("https://maven.enginehub.org/repo/")
}

dependencies {
    val guiceVersion = "7.0.0"
    val invUiVersion = "1.32"
    val configurateVersion = "4.1.2"
    val adventureVersion = "4.3.3"
    val hibernateVersion = "6.6.0.Final"

    compileOnly(files("gradle/libs/PlayerPoints-3.2.7.jar"))
    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
    compileOnly("me.lucko:helper:5.6.14")
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.9")
    compileOnly("com.sk89q.worldedit:worldedit-bukkit:7.3.9")
    compileOnly("com.sk89q.worldedit:worldedit-core:7.2.2-SNAPSHOT")

    // kotlin
    library(kotlin("stdlib"))
    library(kotlin("reflect"))

    //hibernate
    library("org.hibernate.orm:hibernate-core:$hibernateVersion")
    library("org.hibernate.orm:hibernate-hikaricp:$hibernateVersion")

    // guice
    library("com.google.inject:guice:$guiceVersion")
    library("com.google.inject.extensions:guice-assistedinject:$guiceVersion")

    // configurate
    library("org.spongepowered:configurate-yaml:$configurateVersion")
    library("org.spongepowered:configurate-extra-kotlin:$configurateVersion")

    // invui
    library("xyz.xenondevs.invui:invui-core:$invUiVersion")
    library("xyz.xenondevs.invui:invui-kotlin:$invUiVersion")
    (18..20).forEach { library("xyz.xenondevs.invui:inventory-access-r$it:$invUiVersion") }

    // adventure
    library("net.kyori:adventure-platform-bukkit:$adventureVersion")
    library("net.kyori:adventure-text-serializer-bungeecord:$adventureVersion")
    library("net.kyori:adventure-text-minimessage:4.17.0")

    // misc
    library("dev.rollczi:litecommands-bukkit:3.4.3")
    library("dev.rollczi:litecommands-adventure:3.4.1")
    library("io.github.blackbaroness:duration-serializer:2.0.2")
    library("commons-io:commons-io:2.16.1")
    library("org.apache.commons:commons-lang3:3.14.0")
    library("de.tr7zw:item-nbt-api:2.13.1")
    library("org.mariadb.jdbc:mariadb-java-client:3.4.1")
    library("com.google.guava:guava:32.1.2-jre")
    library("com.fasterxml.jackson.core:jackson-databind:2.17.1")

}

kotlin {
    jvmToolchain(21)
}

tasks.compileKotlin.configure {
    compilerOptions.javaParameters = true
}

paper {
    version = "1.0"
    main = "ru.lewis.items.bootstrap.Bootstrap"
    loader = "ru.lewis.items.bootstrap.Loader"
    apiVersion = "1.20"
    author = "Lewis Carrol"
    generateLibrariesJson = true
    foliaSupported = true
    hasOpenClassloader = false

    serverDependencies {
        register("helper") { load = PaperPluginDescription.RelativeLoadOrder.BEFORE }
        register("PlayerPoints") { load = PaperPluginDescription.RelativeLoadOrder.BEFORE }
        register("WorldEdit") { load = PaperPluginDescription.RelativeLoadOrder.BEFORE }
        register("WorldGuard") { load = PaperPluginDescription.RelativeLoadOrder.BEFORE }
    }
}
