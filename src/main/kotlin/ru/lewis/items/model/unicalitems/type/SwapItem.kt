package ru.lewis.items.model.unicalitems.type

import org.bukkit.entity.Player
import ru.lewis.items.configuration.type.UnicalItemType

interface SwapItem : UnicalItem {
    fun addPotionEffect()
    fun removePotionEffect()
    fun applyPlayer(player: Player)
    fun applyType(type: UnicalItemType)
}