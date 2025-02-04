package ru.lewis.items.model.menu.buttons

import jakarta.inject.Inject
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import ru.lewis.items.service.ConfigurationService
import xyz.xenondevs.invui.gui.PagedGui
import xyz.xenondevs.invui.item.ItemProvider
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.controlitem.PageItem

class BackButton @Inject constructor(
    private val configurationService: ConfigurationService
): PageItem(false) {

    private val switchPage get() = configurationService.config.buttons.switchPage

    override fun getItemProvider(p0: PagedGui<*>?): ItemProvider {
        return if (gui.hasPreviousPage()) {
            ItemBuilder(switchPage.previous["yes"]!!.resolve(
                Placeholder.unparsed(
                    "currentpage",
                    (gui.currentPage + 1).toString()
                ),
                Placeholder.unparsed(
                    "pageamount",
                    gui.pageAmount.toString()
                )
            ).toItem())
        } else {
            ItemBuilder(switchPage.previous["no"]!!.toItem())
        }
    }

}