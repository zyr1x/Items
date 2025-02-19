package ru.lewis.items.model.unicalitems.impl

import jakarta.inject.Inject
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerTeleportEvent
import org.bukkit.plugin.Plugin
import ru.lewis.items.configuration.type.UnicalItemType
import ru.lewis.items.extension.playYes
import ru.lewis.items.model.unicalitems.AbstractClickedItem

class FakeEnderPearlItem @Inject constructor(
    override val plugin: Plugin
) : AbstractClickedItem(plugin) {

    private lateinit var teleportPlayer: Player

    override fun click(player: Player) {
        this.teleportPlayer = player
        this.registerEvents()
        player.playYes()
    }

    @EventHandler
    fun onTeleport(event: PlayerTeleportEvent) {
        if (event.cause == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
            if (event.player.uniqueId == teleportPlayer.uniqueId) {
                event.isCancelled = true
                this.unRegisterEvents()
            }
        }
    }

    override fun getType(): UnicalItemType = UnicalItemType.FAKE_ENDER_PEARL
}