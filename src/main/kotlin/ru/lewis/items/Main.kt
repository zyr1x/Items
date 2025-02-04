package ru.lewis.items

import com.google.inject.Inject
import net.kyori.adventure.platform.bukkit.BukkitAudiences
import org.bukkit.plugin.Plugin
import org.slf4j.Logger
import ru.lewis.items.model.SmartLifoCompositeTerminable
import ru.lewis.items.model.listener.ClikedItemListener
import ru.lewis.items.model.unicalitems.manager.UnicalItemManager
import ru.lewis.items.service.CommandService
import ru.lewis.items.service.ConfigurationService
import xyz.xenondevs.invui.InvUI

class Main @Inject constructor(
    private val bukkitAudiences: BukkitAudiences,
    private val configurationService: ConfigurationService,
    private val unicalItemManager: UnicalItemManager,
    private val commandService: CommandService,
    private val clikedItemListener: ClikedItemListener,
    private val plugin: Plugin,
    logger: Logger,
    ) {

    private val terminableRegistry = SmartLifoCompositeTerminable(logger)

    fun start() {

        InvUI.getInstance().setPlugin(plugin)

        audiences = bukkitAudiences
        terminableRegistry.apply {
            with(bukkitAudiences)
            bindModule(configurationService)
            bindModule(commandService)
            bindModule(unicalItemManager)
        }

        this.registerListener()
    }

    fun stop() {
        terminableRegistry.closeAndReportException()
    }

    private fun registerListener() {
        plugin.server.pluginManager.registerEvents(clikedItemListener, plugin)
    }

    companion object {
        lateinit var audiences: BukkitAudiences
    }
}
