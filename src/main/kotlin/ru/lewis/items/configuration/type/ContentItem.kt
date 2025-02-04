package ru.lewis.items.configuration.type

import org.spongepowered.configurate.objectmapping.ConfigSerializable

@ConfigSerializable
data class ContentItem(
    val itemTemplate: ItemTemplate,
    val price: Int
)