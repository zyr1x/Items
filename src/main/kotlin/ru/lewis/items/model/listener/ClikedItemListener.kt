package ru.lewis.items.model.listener

import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.player.PlayerInteractEvent
import ru.lewis.items.configuration.type.ItemTemplate
import ru.lewis.items.model.unicalitems.manager.UnicalItemManager

@Singleton
class ClikedItemListener @Inject constructor(
    private val unicalItemManager: UnicalItemManager
) : Listener {

    @EventHandler
    fun onUse(event: PlayerInteractEvent) {
        val player = event.player
        val action = event.action
        if (action.isRightClick && (action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR)) {
            val item = event.item
            item?.let {
                unicalItemManager.checkAndUse(it, player, ru.lewis.items.model.Action.CLICK)
            }
        }
    }

    @EventHandler
    fun onPlace(event: BlockPlaceEvent) {
        val item = event.itemInHand
        if (ItemTemplate.isDontPlaced(item)) {
            event.isCancelled = true
        }
    }

}