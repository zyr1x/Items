package ru.lewis.items.service

import com.sk89q.worldedit.EditSession
import com.sk89q.worldedit.WorldEdit
import com.sk89q.worldedit.bukkit.BukkitAdapter
import com.sk89q.worldedit.extent.clipboard.Clipboard
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader
import com.sk89q.worldedit.function.operation.Operation
import com.sk89q.worldedit.function.operation.Operations
import com.sk89q.worldedit.math.BlockVector3
import com.sk89q.worldedit.session.ClipboardHolder
import com.sk89q.worldguard.WorldGuard
import com.sk89q.worldguard.protection.managers.RegionManager
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion
import com.sk89q.worldguard.protection.regions.RegionContainer
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.plugin.Plugin
import java.io.FileInputStream
import java.nio.file.Path

@Singleton
class WorldService @Inject constructor(
    private val plugin: Plugin
){

    fun pasteSchematic(path: Path, location: Location): EditSession? {
        return try {
            val blockVector = BukkitAdapter.asBlockVector(location)
            val schematic = path.toFile()
            val clipboardFormat = ClipboardFormats.findByFile(schematic)
            val clipboardReader: ClipboardReader = clipboardFormat?.getReader(FileInputStream(schematic)) ?: return null
            val clipboard: Clipboard = clipboardReader.read()
            val adaptedWorld = BukkitAdapter.adapt(location.world)
            val editSession = WorldEdit.getInstance().newEditSession(adaptedWorld)
            val operation: Operation = ClipboardHolder(clipboard)
                .createPaste(editSession)
                .to(blockVector)
                .build()
            Operations.complete(operation)
            editSession.close()
            editSession
        } catch (exception: Exception) {
            exception.printStackTrace()
            null
        }
    }

    fun removeRegion(region: ProtectedCuboidRegion, world: World) {
        try {
            val container: RegionContainer = WorldGuard.getInstance().platform.regionContainer
            val regionManager: RegionManager = container[BukkitAdapter.adapt(world)] ?: throw Exception("Region manager is null")
            regionManager.removeRegion(region.id)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun createRegionInRadius(regionName: String, center: Location, radius: Int): ProtectedCuboidRegion? {
        return try {
            val container: RegionContainer = WorldGuard.getInstance().platform.regionContainer
            val regionManager: RegionManager = container[BukkitAdapter.adapt(center.world)] ?: throw Exception("Region manager is null")
            val min = BlockVector3.at(center.x - radius, center.y - radius, center.z - radius)
            val max = BlockVector3.at(center.x + radius, center.y + radius, center.z + radius)
            val region = ProtectedCuboidRegion(regionName, min, max)
            regionManager.addRegion(region)
            region
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun isRegionInRadius(center: Location): Boolean {
        return try {
            val world: World = center.world
            val container: RegionContainer = WorldGuard.getInstance().platform.regionContainer
            val regionManager: RegionManager = container[BukkitAdapter.adapt(world)] ?: return false
            val centerVector = BlockVector3.at(center.x, center.y, center.z)

            regionManager.regions.values.any { it.contains(centerVector) }
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

}