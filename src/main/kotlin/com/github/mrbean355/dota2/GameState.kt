package com.github.mrbean355.dota2

import com.google.gson.annotations.SerializedName

data class GameState(

        @SerializedName("provider")
        val provider: Provider,

        @SerializedName("map")
        val map: DotaMap?,

        @SerializedName("player")
        val players: Players?,

        @SerializedName("hero")
        val heroes: Heroes?,

        @SerializedName("abilities")
        val abilities: Abilities?,

        @SerializedName("items")
        val items: Items?

)
