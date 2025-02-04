package ru.lewis.items.service

import dev.rollczi.litecommands.adventure.LiteAdventureExtension
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory
import jakarta.inject.Inject
import jakarta.inject.Singleton
import me.lucko.helper.terminable.TerminableConsumer
import me.lucko.helper.terminable.module.TerminableModule
import net.kyori.adventure.text.minimessage.MiniMessage.miniMessage
import org.bukkit.plugin.Plugin
import ru.lewis.items.commands.ShopCommand

@Singleton
class CommandService @Inject constructor(
    private val plugin: Plugin,
    private val battlePassCommand: ShopCommand
) : TerminableModule {

    @Suppress("UnstableApiUsage")
    override fun setup(consumer: TerminableConsumer) {
        LiteBukkitFactory.builder(plugin.name, plugin)

            .commands(battlePassCommand)

            // extensions
            .extension(LiteAdventureExtension()) { config ->
                config.miniMessage(true)
                config.legacyColor(true)
                config.colorizeArgument(true)
                config.serializer(miniMessage())
            }
                .build()
    }
}