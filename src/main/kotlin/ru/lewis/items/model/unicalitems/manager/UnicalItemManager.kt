package ru.lewis.items.model.unicalitems.manager

import jakarta.inject.Inject
import jakarta.inject.Singleton
import me.lucko.helper.terminable.TerminableConsumer
import me.lucko.helper.terminable.module.TerminableModule
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import ru.lewis.items.configuration.type.ItemTemplate
import ru.lewis.items.configuration.type.UnicalItemType
import ru.lewis.items.model.Action
import ru.lewis.items.model.unicalitems.AbstractAuraItem
import ru.lewis.items.model.unicalitems.AbstractClickedItem
import ru.lewis.items.model.unicalitems.AbstractSwapItem
import ru.lewis.items.model.unicalitems.AbstractUnicalItem
import ru.lewis.items.model.unicalitems.impl.*
import ru.lewis.items.model.unicalitems.type.*
import kotlin.jvm.optionals.getOrNull

@Singleton
class UnicalItemManager @Inject constructor(
    private val fixUnicalItem: FixUnicalItem,
    private val glowUnicalItem: GlowUnicalItem,
    private val featherUnicalItem: FeaterUnicalItem,
    private val leaveUnicalItem: LeaveUnicalItem,
    private val experienceUnicalItem: ExperienceUnicalItem,
    private val crystalProtectAura: CrystalProtectAura,
    private val fallProtectAura: FallProtectAura,
    private val potionUnicalItem: PotionUnicalItem,
    private val fakeEnderPearlItem: FakeEnderPearlItem,
    private val levitationUnicalItem: LevitationUnicalItem,
    private val axeBreakItem: AxeBreakItem,
    private val trapItem: TrapItem
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

    fun checkAndUse(block: Block, player: Player, action: Action) {
        val item = player.inventory.itemInMainHand
        if (item.type == Material.AIR) return
        val itemType = ItemTemplate.getUnicalItemType(item) ?: return
        if (itemType.action != action) return
        items[itemType]?.let {
            when (it) {
                is BreakItem -> (it.copy() as BreakItem).onBreak(player, block)
            }
        }
    }

    fun checkAndGetPotionItem(item: ItemStack, player: Player): AbstractSwapItem? {
        val itemType = ItemTemplate.getUnicalItemType(item) ?: return null
        items[itemType]?.let {
            if (it is SwapItem) {
                val clonedItem = it.copy() as SwapItem
                clonedItem.applyPlayer(player)
                return clonedItem as AbstractSwapItem
            }
        }
        return null
    }

    fun reload() = doReload()

    private fun doReload() {
        items.clear()
        val list = mutableListOf(
            axeBreakItem,
            levitationUnicalItem,
            fixUnicalItem,
            glowUnicalItem,
            leaveUnicalItem,
            featherUnicalItem,
            experienceUnicalItem,
            crystalProtectAura,
            fallProtectAura,
            fakeEnderPearlItem,
            trapItem
        )
        list.addAll(createPotionItems())
        list.forEach { item -> items[item.getType()] = item
        }
    }

    private fun createPotionItems(): List<AbstractSwapItem> {
        return UnicalItemType.POTION.getPotions().map { type ->
            val clonedItem = potionUnicalItem.copy()
            val potionItem = clonedItem as AbstractSwapItem
            potionItem.applyType(type)
            potionItem
        }
    }
}