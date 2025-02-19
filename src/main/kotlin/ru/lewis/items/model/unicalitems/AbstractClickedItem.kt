package ru.lewis.items.model.unicalitems

import jakarta.inject.Inject
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import ru.lewis.items.model.unicalitems.type.ClickableItem

abstract class AbstractClickedItem @Inject constructor(
    override val plugin: Plugin,
) : AbstractUnicalItem(plugin), ClickableItem