package ru.lewis.items.model.unicalitems.impl

import jakarta.inject.Inject
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import org.bukkit.util.Vector
import ru.lewis.items.configuration.type.UnicalItemType
import ru.lewis.items.extension.playYes
import ru.lewis.items.model.Action
import ru.lewis.items.model.unicalitems.AbstractClickedItem

class FeaterUnicalItem @Inject constructor(
    plugin: Plugin
) : AbstractClickedItem(plugin) {

    override fun click(player: Player) {
        this.remove(player, 1)
        val vector = Vector(0, 1, 0)
        player.velocity = vector
        player.playYes()
    }

    override fun getType(): UnicalItemType = UnicalItemType.FEATHER

}