package ru.lewis.items.configuration

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.spongepowered.configurate.objectmapping.ConfigSerializable
import ru.lewis.items.configuration.type.*
import ru.lewis.items.extension.asMiniMessageComponent

@ConfigSerializable
data class SecondMenu(
    val pages: Map<String, MenuConfig> = mapOf(
        "sword" to MenuConfig(
            structure = listOf(
                "x x x x x x x x x",
                "x x x x m x x x x",
                "x x x x x x x x x"
            ),
            title = "<gray>Магазин".asMiniMessageComponent(),
            content = listOf(
                ContentItem(
                    ItemTemplate(
                        Material.DIAMOND_SWORD,
                        displayName = "<red><bold>КРУТОЙ МЕЧ".asMiniMessageComponent(),
                        enchantments = mapOf(
                            Enchantment.DAMAGE_ALL to 9
                        ),
                        unbreakable = true,
                    ),
                    100
                )
            ),
            adapters = listOf(
                AdapterItem(
                    'm',
                    ItemTemplate(
                        Material.BARRIER
                    ),
                    "main"
                )
            )
        )
    )
)