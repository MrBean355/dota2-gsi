package com.github.mrbean355.dota2

import com.google.gson.annotations.SerializedName

data class Ability(

        @SerializedName("name")
        val name: String,

        @SerializedName("level")
        val level: Int,

        @SerializedName("can_cast")
        val canCast: Boolean,

        @SerializedName("passive")
        val passive: Boolean,

        @SerializedName("ability_active")
        val abilityActive: Boolean,

        @SerializedName("cooldown")
        val cooldown: Int,

        @SerializedName("ultimate")
        val ultimate: Boolean
)

class Abilities internal constructor(c: Collection<Ability>) : ArrayList<Ability>(c)
