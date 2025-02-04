package ru.lewis.items.configuration.serializer

import jakarta.inject.Inject
import org.spongepowered.configurate.ConfigurationNode
import org.spongepowered.configurate.kotlin.extensions.set
import org.spongepowered.configurate.serialize.TypeSerializer
import ru.lewis.items.configuration.type.UnicalItemType
import java.lang.reflect.Type

class UnicalItemTypeSerializer @Inject constructor() : TypeSerializer<UnicalItemType> {
    override fun deserialize(type: Type, node: ConfigurationNode): UnicalItemType? {
        return node.string?.let { UnicalItemType.valueOf(it) }
    }

    override fun serialize(type: Type, obj: UnicalItemType?, node: ConfigurationNode) {
        node.set(String::class, obj?.name)
    }
}