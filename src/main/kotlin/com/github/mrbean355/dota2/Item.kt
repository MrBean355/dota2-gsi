package com.github.mrbean355.dota2

import com.google.gson.annotations.SerializedName

data class Item(

        @SerializedName("name")
        val name: String,

        @SerializedName("purchaser")
        val purchaser: Int?,

        @SerializedName("can_cast")
        val canCast: Boolean?,

        @SerializedName("cooldown")
        val cooldown: Int?,

        @SerializedName("passive")
        val passive: Boolean?,

        @SerializedName("charges")
        val charges: Int?

)

data class Items(

        val inventory: List<Item>,

        val stash: List<Item>

)
