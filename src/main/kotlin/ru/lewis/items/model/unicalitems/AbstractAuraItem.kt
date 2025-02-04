package ru.lewis.items.model.unicalitems

import jakarta.inject.Inject
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import ru.lewis.items.configuration.type.ItemTemplate
import ru.lewis.items.model.unicalitems.type.AuraItem

abstract class AbstractAuraItem @Inject constructor(
    override val plugin: Plugin,
) : AbstractUnicalItem(plugin), AuraItem {
    fun check(player: Player): Boolean {
        val inventory = player.inventory
        val nonEmptyItems = inventory.contents
            .filterNotNull()
            .filter { it.type != Material.AIR && it.amount > 0 }

        for (item in nonEmptyItems) {
            val unicalItem = ItemTemplate.getUnicalItemType(item) ?: continue
            if (unicalItem == getType()) {
                return true
            }
        }
        return false
    }
}
