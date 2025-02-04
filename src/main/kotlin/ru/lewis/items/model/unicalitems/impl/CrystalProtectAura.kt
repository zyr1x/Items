package ru.lewis.items.model.unicalitems.impl

import ru.lewis.items.configuration.type.UnicalItemType
import jakarta.inject.Inject
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.plugin.Plugin
import ru.lewis.items.configuration.MessagesConfiguration
import ru.lewis.items.extension.adventure
import ru.lewis.items.model.unicalitems.AbstractAuraItem

class CrystalProtectAura @Inject constructor(
    override val plugin: Plugin,
    private val messagesConfiguration: MessagesConfiguration
) : AbstractAuraItem(plugin) {

    private val crystal get() = messagesConfiguration.aura.crystalProtect

    init {
        this.registerEvents()
    }

    @EventHandler
    fun onDamage(event: EntityDamageByEntityEvent) {
        val damager = event.damager
        if (damager.type != EntityType.ENDER_CRYSTAL) return
        val entity = event.entity
        if (entity !is Player) return
        if (!check(entity)) return
        event.damage /= 2
        entity.adventure.sendMessage(crystal)
    }

    override fun getType(): UnicalItemType = UnicalItemType.AURA.CRYSTAL_PROTECT
}