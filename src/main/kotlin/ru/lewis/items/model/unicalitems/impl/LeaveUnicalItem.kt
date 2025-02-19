package ru.lewis.items.model.unicalitems.impl

import jakarta.inject.Inject
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.plugin.Plugin
import ru.lewis.items.configuration.type.UnicalItemType
import ru.lewis.items.extension.playYes
import ru.lewis.items.model.Action
import ru.lewis.items.model.unicalitems.AbstractClickedItem

class LeaveUnicalItem @Inject constructor(
    plugin: Plugin
) : AbstractClickedItem(plugin) {

    private lateinit var player: Player

    override fun click(player: Player) {
        this.remove(player, 1)
        val location = player.location.clone()
        location.y += 200
        player.teleportAsync(location).thenRun {
            player.playYes()
            this.player = player
            this.registerEvents()
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    fun onDamage(event: EntityDamageEvent) {
        if (event.cause == EntityDamageEvent.DamageCause.FALL) {
            event.isCancelled = true
            this.unRegisterEvents()
        }
    }

    override fun getType(): UnicalItemType = UnicalItemType.LEAVE
}