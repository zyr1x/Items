package ru.lewis.items.configuration.type

import ru.lewis.items.model.Action

sealed interface UnicalItemType {
    val name: String
    val action: Action

    data object FIX : UnicalItemType {
        override val name = "FIX"
        override val action = Action.CLICK
    }

    data object GLOW : UnicalItemType {
        override val name = "GLOW"
        override val action = Action.CLICK
    }

    data object LEAVE : UnicalItemType {
        override val name = "LEAVE"
        override val action = Action.CLICK
    }

    data object FEATHER : UnicalItemType {
        override val name = "FEATHER"
        override val action = Action.CLICK
    }

    data object EXPERIENCE : UnicalItemType {
        override val name = "EXPERIENCE"
        override val action = Action.CLICK
    }

    sealed interface AURA : UnicalItemType {
        data object CRYSTAL_PROTECT : AURA {
            override val name = "CRYSTAL_PROTECT"
            override val action = Action.INVENTORY
        }
        data object FALL_PROTECT : AURA {
            override val name = "FALL_PROTECT"
            override val action = Action.INVENTORY
        }
    }

    data class PotionItem(override val name: String, override val action: Action) : UnicalItemType

    data object POTION : UnicalItemType {
        override val name = "POTION"
        override val action = Action.INVENTORY

        private val potions = mutableMapOf<String, PotionItem>()

        fun addPotion(name: String, action: Action) {
            potions[name] = PotionItem(name, action)
        }

        fun getPotions(): Collection<PotionItem> = potions.values

        fun getPotion(name: String): PotionItem? = potions[name]
    }

    companion object {
        private val VALUES = mutableMapOf(
            FIX.name to FIX,
            GLOW.name to GLOW,
            LEAVE.name to LEAVE,
            FEATHER.name to FEATHER,
            EXPERIENCE.name to EXPERIENCE,
            AURA.CRYSTAL_PROTECT.name to AURA.CRYSTAL_PROTECT,
            AURA.FALL_PROTECT.name to AURA.FALL_PROTECT
        )

        fun valueOf(name: String): UnicalItemType =
            VALUES[name] ?: POTION.getPotion(name)
            ?: throw IllegalArgumentException("No UnicalItemType with name $name")

        fun register(type: UnicalItemType) {
            VALUES[type.name] = type
        }
    }
}

