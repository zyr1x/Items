package ru.lewis.items.model.unicalitems

import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.plugin.Plugin
import ru.lewis.items.configuration.type.UnicalItemType

abstract class AbstractUnicalItem(
    open val plugin: Plugin
) : Cloneable, Listener {

    fun remove(player: Player, amount: Int) {
        val inventory = player.inventory
        val itemInMainHand = inventory.itemInMainHand
        itemInMainHand.amount -= amount
    }

    fun registerEvents() {
        plugin.server.pluginManager.registerEvents(this, plugin)
    }

    fun unRegisterEvents() {
        HandlerList.unregisterAll(this)
    }

    open fun copy(): AbstractUnicalItem {
        return this.clone() as AbstractUnicalItem
    }

}