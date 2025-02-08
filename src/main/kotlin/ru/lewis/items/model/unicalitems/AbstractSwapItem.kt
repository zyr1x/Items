package ru.lewis.items.model.unicalitems

import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitTask
import ru.lewis.items.configuration.type.UnicalItemType
import ru.lewis.items.model.unicalitems.type.SwapItem

abstract class AbstractSwapItem(
    override val plugin: Plugin
) : AbstractUnicalItem(plugin), SwapItem {
    protected lateinit var player: Player
    protected lateinit var unicalItemType: UnicalItemType
}