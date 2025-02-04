package ru.lewis.items.configuration.type

import org.spongepowered.configurate.objectmapping.ConfigSerializable

@ConfigSerializable
data class AdapterItem(
    val symbol: Char,
    val item: ItemTemplate,
    val key: String
)