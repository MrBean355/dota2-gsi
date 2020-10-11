package com.github.mrbean355.dota2

import com.google.gson.annotations.SerializedName

data class DotaMap(

        @SerializedName("name")
        val name: String,

        @SerializedName("matchid")
        val matchId: String,

        @SerializedName("game_time")
        val gameTime: Long,

        @SerializedName("clock_time")
        val clockTime: Long,

        @SerializedName("daytime")
        val daytime: Boolean,

        @SerializedName("nightstalker_night")
        val nightStalkerNight: Boolean,

        @SerializedName("game_state")
        val matchState: MatchState,

        @SerializedName("paused")
        val paused: Boolean,

        @SerializedName("win_team")
        val winningTeam: String,

        @SerializedName("customgamename")
        val customGameName: String,

        // BEGIN: playing

        @SerializedName("ward_purchase_cooldown")
        val wardPurchaseCooldown: Long?,

        // BEGIN: spectating

        @SerializedName("radiant_ward_purchase_cooldown")
        val radiantWardPurchaseCooldown: Long?,

        @SerializedName("dire_ward_purchase_cooldown")
        val direWardPurchaseCooldown: Long?,

        @SerializedName("roshan_state")
        val roshanState: String?,

        @SerializedName("roshan_state_end_seconds")
        val roshanStateEndSeconds: Long?,

        @SerializedName("radiant_win_chance")
        val radiantWinChance: Long?

)
