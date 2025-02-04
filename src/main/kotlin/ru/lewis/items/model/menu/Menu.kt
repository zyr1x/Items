package ru.lewis.items.model.menu

import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.bukkit.entity.Player
import ru.lewis.items.configuration.type.MenuConfig
import ru.lewis.items.configuration.type.import
import ru.lewis.items.configuration.type.playAnim
import ru.lewis.items.model.AssistedInjectFactories
import ru.lewis.items.service.ConfigurationService
import xyz.xenondevs.invui.window.Window

@Singleton
class Menu @Inject constructor(
    private val configurationService: ConfigurationService,
    private val assistedInjectFactories: AssistedInjectFactories
) {
    private val main get() = configurationService.main.template

    fun openMainMenu(player: Player) {
        Window.single().apply {
            val gui = import(main) {
                addIngredient(
                    '<',
                    assistedInjectFactories.createBackButton()
                )

                addIngredient(
                    '>',
                    assistedInjectFactories.createForwardButton()
                )

                main.adapters.forEach { adapter ->
                    addIngredient(
                        adapter.symbol,
                        assistedInjectFactories.createAdapterButton(
                            adapter.key,
                            adapter.item
                        )
                    )
                }

                setContent(
                    main.content.map {
                        assistedInjectFactories.createContentButton(it)
                    }
                )

            }
            open(player)
            gui.playAnim()
        }
    }

    fun openSecondMenu(player: Player, menuConfig: MenuConfig) {
        Window.single().apply {
            val gui = import(menuConfig) {
                addIngredient(
                    '<',
                    assistedInjectFactories.createBackButton()
                )

                addIngredient(
                    '>',
                    assistedInjectFactories.createForwardButton()
                )

                menuConfig.adapters.forEach { adapter ->
                    addIngredient(
                        adapter.symbol,
                        assistedInjectFactories.createAdapterButton(
                            adapter.key,
                            adapter.item
                        )
                    )
                }

                setContent(
                    menuConfig.content.map {
                        assistedInjectFactories.createContentButton(it)
                    }
                )

            }
            open(player)
            gui.playAnim()
        }
    }

}