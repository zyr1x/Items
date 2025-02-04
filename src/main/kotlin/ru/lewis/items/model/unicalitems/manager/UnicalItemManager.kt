package ru.lewis.items.model.unicalitems.manager

import jakarta.inject.Inject
import jakarta.inject.Singleton
import me.lucko.helper.terminable.TerminableConsumer
import me.lucko.helper.terminable.module.TerminableModule
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import ru.lewis.items.configuration.type.ItemTemplate
import ru.lewis.items.configuration.type.UnicalItemType
import ru.lewis.items.model.Action
import ru.lewis.items.model.unicalitems.AbstractAuraItem
import ru.lewis.items.model.unicalitems.AbstractClickedItem
import ru.lewis.items.model.unicalitems.AbstractUnicalItem
import ru.lewis.items.model.unicalitems.impl.*
import ru.lewis.items.model.unicalitems.type.AuraItem
import ru.lewis.items.model.unicalitems.type.ClickableItem
import ru.lewis.items.model.unicalitems.type.UnicalItem
import kotlin.jvm.optionals.getOrNull

@Singleton
class UnicalItemManager @Inject constructor(
    private val fixUnicalItem: FixUnicalItem,
    private val glowUnicalItem: GlowUnicalItem,
    private val featherUnicalItem: FeaterUnicalItem,
    private val leaveUnicalItem: LeaveUnicalItem,
    private val experienceUnicalItem: ExperienceUnicalItem,
    private val crystalProtectAura: CrystalProtectAura,
    private val fallProtectAura: FallProtectAura
): TerminableModule {

    private val items: MutableMap<UnicalItemType, AbstractUnicalItem> = mutableMapOf()

    override fun setup(p0: TerminableConsumer) {
        doReload()
    }

    fun checkAndUse(item: ItemStack, player: Player, action: Action) {
        val itemType = ItemTemplate.getUnicalItemType(item) ?: return
        if (itemType.action != action) return
        items[itemType]?.let {
            when (it) {
                is ClickableItem -> (it.copy() as ClickableItem).click(player)
            }
        }
    }

    fun reload() = doReload()

    private fun doReload() {
        items.clear()
        listOf(
            fixUnicalItem,
            glowUnicalItem,
            leaveUnicalItem,
            featherUnicalItem,
            experienceUnicalItem,
            crystalProtectAura,
            fallProtectAura
        ).forEach { item ->
            items[item.getType()] = item
        }
    }
}