package ru.lewis.items.configuration

import org.bukkit.Material
import org.spongepowered.configurate.objectmapping.ConfigSerializable
import ru.lewis.items.configuration.type.AdapterItem
import ru.lewis.items.configuration.type.ItemTemplate
import ru.lewis.items.configuration.type.MenuConfig
import ru.lewis.items.extension.asMiniMessageComponent

@ConfigSerializable
data class MainMenu(
    val template: MenuConfig = MenuConfig(
        structure = listOf(
            "x x x x x x x x x",
            "x x x x s x x x x",
            "x x x x x x x x x"
        ),
        title = "<gray>Магазин".asMiniMessageComponent(),
        adapters = listOf(
            AdapterItem(
                's',
                ItemTemplate(
                    Material.DIAMOND_SWORD
                ),
                "sword"
            )
        )
    )
)