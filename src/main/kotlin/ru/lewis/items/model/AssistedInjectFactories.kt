package ru.lewis.items.model

import ru.lewis.items.configuration.type.ContentItem
import ru.lewis.items.configuration.type.ItemTemplate
import ru.lewis.items.model.menu.buttons.AdapterButton
import ru.lewis.items.model.menu.buttons.BackButton
import ru.lewis.items.model.menu.buttons.ContentButton
import ru.lewis.items.model.menu.buttons.ForwardButton

interface AssistedInjectFactories {

    fun createBackButton(): BackButton

    fun createForwardButton(): ForwardButton

    fun createAdapterButton(
        pageKey: String,
        itemTemplate: ItemTemplate
    ): AdapterButton

    fun createContentButton(
        contentItem: ContentItem
    ): ContentButton

}