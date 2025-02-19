package ru.lewis.items.model.unicalitems

import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable
import ru.lewis.items.model.unicalitems.type.BreakItem

abstract class AbstractBreakItem(
    override val plugin: Plugin
) : AbstractUnicalItem(plugin), BreakItem {

    private val ADJACENT_FACES = arrayOf(
        BlockFace.UP, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST
    )

    fun getTreeBlocks(startBlock: Block): Set<Block> {
        if (!isLog(startBlock)) return emptySet()

        val logs = mutableSetOf<Block>()
        val leaves = mutableSetOf<Block>()

        findTreeBlocks(startBlock, logs, leaves)

        return logs + leaves
    }

    private fun findTreeBlocks(block: Block, logs: MutableSet<Block>, leaves: MutableSet<Block>) {
        if (!isLog(block) || !logs.add(block)) return

        for (face in ADJACENT_FACES) {
            findTreeBlocks(block.getRelative(face), logs, leaves)
        }

        for (dx in -2..2) {
            for (dy in -2..2) {
                for (dz in -2..2) {
                    val relative = block.getRelative(dx, dy, dz)
                    if (isLeaf(relative)) leaves.add(relative)
                }
            }
        }
    }

    fun destroyTree(startBlock: Block) {
        val treeBlocks = getTreeBlocks(startBlock).toList()

        treeBlocks.forEachIndexed { index, block ->
            object : BukkitRunnable() {
                override fun run() {
                    if (block.type != Material.AIR) {
                        block.world.playSound(block.location, Sound.BLOCK_WOOD_BREAK, 1.0f, 1.0f)

                        block.breakNaturally()
                    }
                }
            }.runTaskLater(plugin, (index * 2L))
        }
    }

    private fun isLog(block: Block) = block.type.name.endsWith("_LOG")

    private fun isLeaf(block: Block) = block.type.name.endsWith("_LEAVES")

}