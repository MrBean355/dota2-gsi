package com.github.mrbean355.dota2

import com.google.gson.annotations.SerializedName

@Suppress("unused")
enum class MatchState {

    @SerializedName("DOTA_GAMERULES_STATE_DISCONNECT")
    DISCONNECT,

    @SerializedName("DOTA_GAMERULES_STATE_GAME_IN_PROGRESS")
    IN_PROGRESS,

    @SerializedName("DOTA_GAMERULES_STATE_HERO_SELECTION")
    HERO_SELECTION,

    @SerializedName("DOTA_GAMERULES_STATE_INIT")
    INIT,

    @SerializedName("DOTA_GAMERULES_STATE_LAST")
    LAST,

    @SerializedName("DOTA_GAMERULES_STATE_POST_GAME")
    POST_GAME,

    @SerializedName("DOTA_GAMERULES_STATE_PRE_GAME")
    PRE_GAME,

    @SerializedName("DOTA_GAMERULES_STATE_STRATEGY_TIME")
    STRATEGY_TIME,

    @SerializedName("DOTA_GAMERULES_STATE_WAIT_FOR_PLAYERS_TO_LOAD")
    WAIT_FOR_PLAYERS_TO_LOAD,

    @SerializedName("DOTA_GAMERULES_STATE_CUSTOM_GAME_SETUP")
    CUSTOM_GAME_SETUP

}
