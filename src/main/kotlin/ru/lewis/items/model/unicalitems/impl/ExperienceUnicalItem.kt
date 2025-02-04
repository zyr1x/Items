package ru.lewis.items.model.unicalitems.impl

import jakarta.inject.Inject
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import ru.lewis.items.configuration.type.UnicalItemType
import ru.lewis.items.extension.playYes
import ru.lewis.items.model.Action
import ru.lewis.items.model.unicalitems.AbstractClickedItem

class ExperienceUnicalItem @Inject constructor(
    plugin: Plugin
): AbstractClickedItem(plugin) {
    override fun click(player: Player) {
        this.remove(player, 1)
        player.giveExpLevels(10)
        player.playYes()
    }

    override fun getType(): UnicalItemType = UnicalItemType.EXPERIENCE
}