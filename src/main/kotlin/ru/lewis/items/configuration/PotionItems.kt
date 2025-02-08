package ru.lewis.items.configuration

import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.potion.PotionType
import org.spongepowered.configurate.objectmapping.ConfigSerializable
import ru.lewis.items.configuration.type.ItemTemplate.PotionTemplate
import ru.lewis.items.configuration.type.UnicalItemType
import ru.lewis.items.model.Action
import java.awt.Color

@ConfigSerializable
data class PotionItems(
    val items: Map<String, PotionTemplate> = mapOf(
        "SPEED" to PotionTemplate(
            PotionType.UNCRAFTABLE,
            extended = true,
            upgraded = true,
            color = Color.YELLOW,
            effects = listOf(
                PotionEffect(
                    PotionEffectType.SPEED,
                    20 * 30, 1
                )
            )
        )
    )
) {

    init {
        this.items.forEach {item ->
            UnicalItemType.POTION.addPotion(item.key, Action.SWAP)
        }
    }

}