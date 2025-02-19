package ru.lewis.items.model.unicalitems.impl

import com.sk89q.worldedit.EditSession
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion
import jakarta.inject.Inject
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import ru.lewis.items.configuration.type.UnicalItemType
import ru.lewis.items.model.unicalitems.AbstractClickedItem
import ru.lewis.items.service.ConfigurationService
import ru.lewis.items.service.WorldService
import java.nio.file.Path
import java.util.UUID

class TrapItem @Inject constructor(
    override val plugin: Plugin,
    private val worldService: WorldService,
    private val configurationService: ConfigurationService
) : AbstractClickedItem(plugin) {
    private val cfg get() = configurationService.config
    private val path get(): Path = plugin.dataFolder.toPath().resolve(cfg.trapSchematicPath)
    private lateinit var editSession: EditSession
    private lateinit var region: ProtectedCuboidRegion
    private lateinit var world: World

    override fun click(player: Player) {
        val location = player.location.clone()
        location.y -= 1

        if (worldService.isRegionInRadius(location)) return
        this.remove(player, 1)

        worldService.pasteSchematic(path, location)?.let {
            editSession = it
            world = location.world
        }
        worldService.createRegionInRadius(UUID.randomUUID().toString(), location, 5)?.let {
            region = it
        }

        startRemoveTask()
    }

    private fun startRemoveTask() {
        Bukkit.getScheduler().runTaskLater(plugin, Runnable {
            if (::region.isInitialized && ::world.isInitialized) {
                worldService.removeRegion(region, world)
            }
            if (::editSession.isInitialized) {
                editSession.undo(editSession)
            }
        }, 20L * 20L)
    }

    override fun getType(): UnicalItemType = UnicalItemType.TRAP
}