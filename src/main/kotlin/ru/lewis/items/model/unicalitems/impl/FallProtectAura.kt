package ru.lewis.items.model.unicalitems.impl

import jakarta.inject.Inject
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.plugin.Plugin
import ru.lewis.items.configuration.MessagesConfiguration
import ru.lewis.items.configuration.type.UnicalItemType
import ru.lewis.items.extension.adventure
import ru.lewis.items.model.unicalitems.AbstractAuraItem

class FallProtectAura @Inject constructor(
    override val plugin: Plugin,
    private val messagesConfiguration: MessagesConfiguration
) : AbstractAuraItem(plugin) {

    private val fall get() = messagesConfiguration.aura.fallProtect

    init {
        this.registerEvents()
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onDamage(event: EntityDamageEvent) {
        val cause = event.cause
        if (cause != EntityDamageEvent.DamageCause.FALL) return
        val entity = event.entity
        if (entity !is Player) return
        if (!check(entity)) return
        event.damage *= 0.2
        entity.adventure.sendMessage(fall)
    }

    override fun getType(): UnicalItemType = UnicalItemType.AURA.FALL_PROTECT
}