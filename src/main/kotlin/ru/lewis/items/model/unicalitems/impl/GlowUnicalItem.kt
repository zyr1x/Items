package ru.lewis.items.model.unicalitems.impl

import jakarta.inject.Inject
import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.Particle
import org.bukkit.World
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.util.Vector
import ru.lewis.items.configuration.type.UnicalItemType
import ru.lewis.items.extension.playYes
import ru.lewis.items.model.Action
import ru.lewis.items.model.unicalitems.AbstractClickedItem
import kotlin.math.cos
import kotlin.math.sin

class GlowUnicalItem @Inject constructor(
    override val plugin: Plugin
) : AbstractClickedItem(plugin) {

    override fun click(player: Player) {
        this.remove(player, 1)
        this.drawCircle(player)
        player.playYes()
    }

    override fun getType(): UnicalItemType = UnicalItemType.GLOW

    private fun drawCircle(owner: Player) {
        val world: World = owner.world
        val center = owner.location.clone()
        var radius = 0.0
        val maxRadius = 5.0
        val step = 0.2
        val interval = 1L
        val dustOptions = Particle.DustOptions(Color.WHITE, 1.5f)

        var taskId = 0
        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, {
            if (radius > maxRadius) {
                Bukkit.getScheduler().cancelTask(taskId)
                return@scheduleSyncRepeatingTask
            }

            val points = 100
            for (i in 0 until points) {
                val angle = 2 * Math.PI * i / points
                val x = cos(angle) * radius
                val z = sin(angle) * radius
                val particleLocation = center.clone().add(Vector(x, 0.0, z))
                world.spawnParticle(Particle.REDSTONE, particleLocation, 1, dustOptions)
            }

            world.getNearbyEntities(center, radius, radius, radius)
                .filterIsInstance<Player>()
                .filter { it != owner }
                .forEach { player ->
                    player.addPotionEffect(
                        PotionEffect(PotionEffectType.GLOWING, 20 * 5, 0)
                    )
                }

            radius += step
        }, 0L, interval)
    }

}