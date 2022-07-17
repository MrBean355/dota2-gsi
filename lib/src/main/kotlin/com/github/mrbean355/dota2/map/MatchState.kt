/*
 * Copyright 2022 Michael Johnston
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

package com.github.mrbean355.dota2.map

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Suppress("unused")
enum class MatchState {

    @SerialName("DOTA_GAMERULES_STATE_DISCONNECT")
    DISCONNECT,

    @SerialName("DOTA_GAMERULES_STATE_GAME_IN_PROGRESS")
    GAME_IN_PROGRESS,

    @SerialName("DOTA_GAMERULES_STATE_HERO_SELECTION")
    HERO_SELECTION,

    @SerialName("DOTA_GAMERULES_STATE_INIT")
    INIT,

    @SerialName("DOTA_GAMERULES_STATE_LAST")
    LAST,

    @SerialName("DOTA_GAMERULES_STATE_POST_GAME")
    POST_GAME,

    @SerialName("DOTA_GAMERULES_STATE_PRE_GAME")
    PRE_GAME,

    @SerialName("DOTA_GAMERULES_STATE_STRATEGY_TIME")
    STRATEGY_TIME,

    @SerialName("DOTA_GAMERULES_STATE_WAIT_FOR_PLAYERS_TO_LOAD")
    WAIT_FOR_PLAYERS_TO_LOAD,

    @SerialName("DOTA_GAMERULES_STATE_CUSTOM_GAME_SETUP")
    CUSTOM_GAME_SETUP

}
