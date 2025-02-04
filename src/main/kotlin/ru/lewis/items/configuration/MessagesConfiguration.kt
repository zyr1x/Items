package ru.lewis.items.configuration

import org.spongepowered.configurate.objectmapping.ConfigSerializable
import ru.lewis.items.configuration.type.MiniMessageComponent
import ru.lewis.items.extension.asMiniMessageComponent

@ConfigSerializable
data class MessagesConfiguration(
    val aura: Aura = Aura()
) {

    @ConfigSerializable
    data class Aura(
        val crystalProtect: MiniMessageComponent = "Аура кристалла вас защитила!".asMiniMessageComponent(),
        val fallProtect: MiniMessageComponent = "Аура падения вас защитила!".asMiniMessageComponent()
    )
}