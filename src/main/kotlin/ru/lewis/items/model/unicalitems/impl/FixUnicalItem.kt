package ru.lewis.items.model.unicalitems.impl

import jakarta.inject.Inject
import org.bukkit.entity.Player
import org.bukkit.inventory.meta.Damageable
import org.bukkit.plugin.Plugin
import ru.lewis.items.configuration.type.UnicalItemType
import ru.lewis.items.extension.playYes
import ru.lewis.items.model.Action
import ru.lewis.items.model.unicalitems.AbstractClickedItem

class FixUnicalItem @Inject constructor(
    plugin: Plugin
) : AbstractClickedItem(plugin) {

    override fun click(player: Player) {
        this.remove(player, 1)
        val inventory = player.inventory
        val armorContents = inventory.armorContents
        armorContents.forEach { armor ->
            armor?.let {
                val meta = armor.itemMeta
                if (meta is Damageable) {
                    meta.damage = 0
                    armor.itemMeta = meta
                }
            }
        }
        player.playYes()
    }

    override fun getType(): UnicalItemType = UnicalItemType.FIX

}