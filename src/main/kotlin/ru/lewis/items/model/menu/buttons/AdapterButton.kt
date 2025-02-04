package ru.lewis.items.model.menu.buttons

import com.google.inject.assistedinject.Assisted
import jakarta.inject.Inject
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import ru.lewis.items.configuration.type.ItemTemplate
import ru.lewis.items.model.menu.Menu
import ru.lewis.items.service.ConfigurationService
import xyz.xenondevs.invui.item.ItemProvider
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.AbstractItem

class AdapterButton @Inject constructor(
    private val menu: Menu,
    private val configurationService: ConfigurationService,
    @Assisted private val pageKey: String,
    @Assisted private val itemTemplate: ItemTemplate
) : AbstractItem() {
    private val pages get() = configurationService.second.pages

    override fun getItemProvider(): ItemProvider = ItemBuilder(itemTemplate.toItem())

    override fun handleClick(p0: ClickType, player: Player, p2: InventoryClickEvent) {
        pages.forEach { page ->
            if (pageKey == "main") {
                menu.openMainMenu(player)
                return@forEach
            }
            if (page.key == pageKey) {
                menu.openSecondMenu(player, page.value)
                return@forEach
            }
        }
    }
}