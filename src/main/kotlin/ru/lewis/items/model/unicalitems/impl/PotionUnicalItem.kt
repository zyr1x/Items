package ru.lewis.items.model.unicalitems.impl

import jakarta.inject.Inject
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitTask
import ru.lewis.items.configuration.type.UnicalItemType
import ru.lewis.items.extension.runSync
import ru.lewis.items.model.unicalitems.AbstractSwapItem
import ru.lewis.items.service.ConfigurationService
import java.util.concurrent.ConcurrentHashMap

class PotionUnicalItem @Inject constructor(
    private val configurationService: ConfigurationService,
    override val plugin: Plugin
) : AbstractSwapItem(plugin) {

    private val items get() = configurationService.potionItems.items

    override fun addPotionEffect() {
        runSync {
            items[getType().name]?.effects?.let { effects ->
                effects.forEach { player.addPotionEffect(it) }
            }
        }
    }

    override fun removePotionEffect() {
        runSync {
            items[getType().name]?.effects?.forEach { player.removePotionEffect(it.type) }
        }
    }

    override fun applyPlayer(player: Player) {
        this.player = player
    }

    override fun applyType(type: UnicalItemType) {
        this.unicalItemType = type
    }

    override fun getType(): UnicalItemType = unicalItemType
}