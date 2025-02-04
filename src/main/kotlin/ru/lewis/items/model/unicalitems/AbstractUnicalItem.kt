package ru.lewis.items.model.unicalitems

import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.plugin.Plugin
import ru.lewis.items.configuration.type.UnicalItemType

abstract class AbstractUnicalItem(
    open val plugin: Plugin
) : Cloneable, Listener {

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