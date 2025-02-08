package ru.lewis.items.model.task

import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable
import ru.lewis.items.model.unicalitems.manager.UnicalItemManager

@Singleton
class PotionItemChecker @Inject constructor(
    private val plugin: Plugin,
    private val unicalItemManager: UnicalItemManager
) : BukkitRunnable() {
    override fun run() {
        val players = Bukkit.getOnlinePlayers()
        for (player: Player in players) {
            val itemInOffHand = player.inventory.itemInOffHand
            if (itemInOffHand.type != Material.AIR) {
                unicalItemManager.checkAndGetPotionItem(itemInOffHand, player)?.addPotionEffect()
            }
        }
    }
}