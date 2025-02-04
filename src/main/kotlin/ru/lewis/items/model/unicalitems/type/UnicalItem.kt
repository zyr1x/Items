package ru.lewis.items.model.unicalitems.type

import ru.lewis.items.configuration.type.UnicalItemType

interface UnicalItem {
    fun getType(): UnicalItemType
}