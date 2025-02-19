package ru.lewis.items.model.unicalitems.type

import org.bukkit.block.Block
import org.bukkit.entity.Player

interface BreakItem : UnicalItem {
    fun onBreak(player: Player, block: Block)
}