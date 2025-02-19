package ru.lewis.items.model.unicalitems.impl

import jakarta.inject.Inject
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import ru.lewis.items.configuration.type.UnicalItemType
import ru.lewis.items.model.unicalitems.AbstractBreakItem

class AxeBreakItem @Inject constructor(
    override val plugin: Plugin
) : AbstractBreakItem(plugin) {

    override fun onBreak(player: Player, block: Block) {
        this.remove(player, 1)
        destroyTree(block)
    }

    override fun getType(): UnicalItemType = UnicalItemType.TREE_AXE
}