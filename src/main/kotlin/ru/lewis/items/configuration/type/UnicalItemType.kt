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

    companion object {
        private val VALUES = listOf(
            FIX, GLOW, LEAVE, FEATHER, EXPERIENCE,
            AURA.CRYSTAL_PROTECT, AURA.FALL_PROTECT
        ).associateBy { it.name }

        fun valueOf(name: String): UnicalItemType =
            VALUES[name] ?: throw IllegalArgumentException("No ru.lewis.items.configuration.type.UnicalItemType with name $name")
    }
}
