package ru.lewis.items.model.unicalitems.type

import org.bukkit.entity.Player

interface ClickableItem : UnicalItem {
    fun click(player: Player)
}