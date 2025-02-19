package ru.lewis.items.model.listener

import jakarta.inject.Inject
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import ru.lewis.items.model.Action
import ru.lewis.items.model.unicalitems.manager.UnicalItemManager

class BreakItemListener @Inject constructor(
    private val unicalItemManager: UnicalItemManager
) : Listener {

    @EventHandler
    fun onBreak(event: BlockBreakEvent) {
        val player = event.player
        val block = event.block
        unicalItemManager.checkAndUse(block, player, Action.BREAK)
    }

}