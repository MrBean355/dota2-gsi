/*
 * Copyright 2021 Michael Johnston
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.mrbean355.dota2

import com.google.gson.annotations.SerializedName

@Suppress("unused")
enum class MatchState {

    @SerializedName("DOTA_GAMERULES_STATE_DISCONNECT")
    DISCONNECT,

    @SerializedName("DOTA_GAMERULES_STATE_GAME_IN_PROGRESS")
    GAME_IN_PROGRESS,

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
