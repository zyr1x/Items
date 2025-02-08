package ru.lewis.items.service

import com.google.inject.Inject
import com.google.inject.Singleton
import me.lucko.helper.terminable.TerminableConsumer
import me.lucko.helper.terminable.module.TerminableModule
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.EntityType
import org.bukkit.inventory.ItemFlag
import org.bukkit.plugin.Plugin
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.potion.PotionType
import org.slf4j.Logger
import org.spongepowered.configurate.kotlin.extensions.get
import org.spongepowered.configurate.kotlin.extensions.set
import org.spongepowered.configurate.kotlin.objectMapperFactory
import org.spongepowered.configurate.yaml.NodeStyle
import org.spongepowered.configurate.yaml.YamlConfigurationLoader
import ru.lewis.items.configuration.*
import ru.lewis.items.configuration.serializer.*
import ru.lewis.items.configuration.type.BossBarConfiguration
import ru.lewis.items.configuration.type.MiniMessageComponent
import ru.lewis.items.configuration.type.UnicalItemType
import java.awt.Color
import java.time.Duration
import kotlin.io.path.*

@Singleton
class ConfigurationService @Inject constructor(
    private val plugin: Plugin,
    private val miniMessageComponentSerializer: MiniMessageComponentSerializer,
    private val colorSerializer: ColorSerializer,
    private val durationSerializer: DurationSerializer,
    private val potionEffectSerializer: PotionEffectSerializer,
    private val bossBarConfigurationSerializer: BossBarConfigurationSerializer,
    private val logger: Logger,
    private val potionEffectTypeSerializer: PotionEffectTypeSerializer,
    private val materialSerializer: MaterialSerializer,
    private val entityTypeSerializer: EntityTypeSerializer,
    private val enchantmentSerializer: EnchantmentSerializer,
    private val itemFlagSerializer: ItemFlagSerializer,
    private val attributeModifierSerializer: AttributeModifierSerializer,
    private val attributeSerializer: AttributeSerializer,
    private val potionTypeSerializer: PotionTypeSerializer,
    private val unicalItemTypeSerializer: UnicalItemTypeSerializer
) : TerminableModule {

    lateinit var config: Configuration
        private set

    lateinit var main: MainMenu
        private set

    lateinit var second: SecondMenu
        private set

    lateinit var messages: MessagesConfiguration
        private set

    lateinit var potionItems: PotionItems
        private set

    private val rootDirectory = Path("")
    private val configFile = plugin.dataFolder.toPath().resolve("config.yml")
    private val mainFile = plugin.dataFolder.toPath().resolve("main_menu.yml")
    private val secondFile = plugin.dataFolder.toPath().resolve("second_menus.yml")
    private val messagesFile = plugin.dataFolder.toPath().resolve("messages.yml")
    private val potionItemsFile = plugin.dataFolder.toPath().resolve("potion_items.yml")
    private val builder = createLoaderBuilder()

    override fun setup(consumer: TerminableConsumer) = doReload()
    fun reload() = doReload()

    fun saveConfig() {
        builder.path(mainFile).build().let {
            it.save(it.createNode().set(main))
        }
        builder.path(secondFile).build().let {
            it.save(it.createNode().set(second))
        }
        builder.path(configFile).build().let {
            it.save(it.createNode().set(config))
        }
        builder.path(messagesFile).build().let {
            it.save(it.createNode().set(messages))
        }
        builder.path(potionItemsFile).build().let {
            it.save(it.createNode().set(potionItems))
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Internal
    ///////////////////////////////////////////////////////////////////////////

    @Synchronized
    private fun doReload() {
        plugin.dataFolder.toPath().createDirectories()
        main = builder.path(mainFile).build().getAndSave<MainMenu>()
        potionItems = builder.path(potionItemsFile).build().getAndSave<PotionItems>()
        second = builder.path(secondFile).build().getAndSave<SecondMenu>()
        config = builder.path(configFile).build().getAndSave<Configuration>()
        messages = builder.path(messagesFile).build().getAndSave<MessagesConfiguration>()
    }

    private fun createLoaderBuilder(): YamlConfigurationLoader.Builder {
        return YamlConfigurationLoader.builder()

            .defaultOptions {
                it.serializers { serializers ->
                    serializers
                        .register(UnicalItemType::class.java, unicalItemTypeSerializer)
                        .register(PotionType::class.java, potionTypeSerializer)
                        .register(Attribute::class.java, attributeSerializer)
                        .register(AttributeModifier::class.java, attributeModifierSerializer)
                        .register(ItemFlag::class.java, itemFlagSerializer)
                        .register(Duration::class.java, durationSerializer)
                        .register(MiniMessageComponent::class.java, miniMessageComponentSerializer)
                        .register(Color::class.java, colorSerializer)
                        .register(PotionEffect::class.java, potionEffectSerializer)
                        .register(BossBarConfiguration::class.java, bossBarConfigurationSerializer)
                        .register(Material::class.java, materialSerializer)
                        .register(PotionEffectType::class.java, potionEffectTypeSerializer)
                        .register(EntityType::class.java, entityTypeSerializer)
                        .register(Enchantment::class.java, enchantmentSerializer)
                        .registerAnnotatedObjects(objectMapperFactory())
                }
            }

            .indent(2)
            .nodeStyle(NodeStyle.BLOCK)
    }

    private inline fun <reified T : Any> YamlConfigurationLoader.getAndSave(): T {
        val obj = this.load().get(T::class)!!
        this.save(this.createNode().set(T::class, obj))
        return obj
    }

    //powered by BlackBoroness

}

