package ru.lewis.items.configuration

import org.bukkit.Material
import org.spongepowered.configurate.objectmapping.ConfigSerializable
import ru.lewis.items.configuration.type.ItemTemplate
import ru.lewis.items.configuration.type.MiniMessageComponent
import ru.lewis.items.extension.asMiniMessageComponent

@ConfigSerializable
data class Configuration(
    val buttons: Buttons = Buttons(),
    val buyLore: List<MiniMessageComponent> = listOf(
        "<gray>Цена: <price> Р".asMiniMessageComponent(),
        "<green>Нажмите, чтобы купить".asMiniMessageComponent()
    ),
    val trapSchematicPath: String = "/schematic/trap.schematic"
) {
    @ConfigSerializable
    data class Buttons(
        val switchPage: SwitchPage = SwitchPage()
    ) {

        @ConfigSerializable
        data class SwitchPage(
            val previous: Map<String, ItemTemplate> = mapOf(
                Pair(
                    "yes",
                    ItemTemplate(
                        Material.ENDER_EYE
                    )
                ),
                Pair(
                    "no",
                    ItemTemplate(
                        Material.ENDER_PEARL
                    )
                )
            ),

            val next: Map<String, ItemTemplate> = mapOf(
                Pair(
                    "yes",
                    ItemTemplate(
                        Material.ENDER_EYE
                    )
                ),
                Pair(
                    "no",
                    ItemTemplate(
                        Material.ENDER_PEARL
                    )
                )
            ),

            val refresh: Map<String, ItemTemplate> = mapOf(
                Pair(
                    "loading",
                    ItemTemplate(
                        Material.BARRIER
                    )
                ),
                Pair(
                    "loaded",
                    ItemTemplate(
                        Material.GREEN_WOOL
                    )
                )
            )
        )

    }
}