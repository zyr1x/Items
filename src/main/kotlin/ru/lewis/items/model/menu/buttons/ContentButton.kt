package ru.lewis.items.model.menu.buttons

import com.google.inject.assistedinject.Assisted
import jakarta.inject.Inject
import net.kyori.adventure.text.minimessage.tag.resolver.Formatter
import org.black_ixx.playerpoints.PlayerPointsAPI
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import ru.lewis.items.configuration.type.ContentItem
import ru.lewis.items.extension.playNo
import ru.lewis.items.extension.playYes
import ru.lewis.items.service.ConfigurationService
import xyz.xenondevs.invui.item.ItemProvider
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.AbstractItem

class ContentButton @Inject constructor(
    private val configurationService: ConfigurationService,
    private val pointsAPI: PlayerPointsAPI,
    @Assisted private val contentItem: ContentItem
) : AbstractItem() {
    private val lore get() = configurationService.config.buyLore

    override fun getItemProvider(): ItemProvider {
        return ItemBuilder(
            contentItem.itemTemplate.resolve(
                lore,
                Formatter.number(
                    "price",
                    contentItem.price
                )
            ).toItem()
        )
    }

    override fun handleClick(p0: ClickType, player: Player, p2: InventoryClickEvent) {
        if (!pointsAPI.take(
                player.uniqueId,
                contentItem.price
            )) {
            player.playNo()
            return
        }
        player.inventory.addItem(
            contentItem.itemTemplate.toItem()
        )
        player.playYes()
    }

}