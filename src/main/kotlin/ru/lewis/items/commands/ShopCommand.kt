package ru.lewis.items.commands

import dev.rollczi.litecommands.annotations.argument.Arg
import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import dev.rollczi.litecommands.annotations.permission.Permission
import jakarta.inject.Inject
import org.bukkit.entity.Player
import ru.lewis.items.model.menu.Menu
import ru.lewis.items.model.unicalitems.manager.UnicalItemManager
import ru.lewis.items.service.ConfigurationService

@Command(name = "shop", aliases = ["магазин"])
class ShopCommand @Inject constructor(
    private val menu: Menu,
    private val configurationService: ConfigurationService,
    private val unicalItemManager: UnicalItemManager
){
    private val main get() = configurationService.main.template
    private val pages get() = configurationService.second.pages

    @Execute
    fun execute(@Context player: Player) {
        menu.openMainMenu(player)
    }

    @Execute(name = "open")
    fun openSpecificTab(@Context player: Player, @Arg tab: String) {
        for (page in pages) {
            if (page.key == tab) {
                menu.openSecondMenu(player, page.value)
                return
            }
        }
    }

    @Permission("items.reload")
    @Execute(name = "reload")
    fun reload() {
        configurationService.reload()
        unicalItemManager.reload()
    }

}

/*

 */