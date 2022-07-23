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

@file:Suppress("unused")

package com.github.mrbean355.dota2.map

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class MatchState {

    @SerialName("DOTA_GAMERULES_STATE_DISCONNECT")
    Disconnect,

    @SerialName("DOTA_GAMERULES_STATE_GAME_IN_PROGRESS")
    GameInProgress,

    @SerialName("DOTA_GAMERULES_STATE_HERO_SELECTION")
    HeroSelection,

    @SerialName("DOTA_GAMERULES_STATE_INIT")
    Init,

    @SerialName("DOTA_GAMERULES_STATE_LAST")
    Last,

    @SerialName("DOTA_GAMERULES_STATE_POST_GAME")
    PostGame,

    @SerialName("DOTA_GAMERULES_STATE_PRE_GAME")
    PreGame,

    @SerialName("DOTA_GAMERULES_STATE_STRATEGY_TIME")
    StrategyTime,

    @SerialName("DOTA_GAMERULES_STATE_WAIT_FOR_PLAYERS_TO_LOAD")
    WaitForPlayersToLoad,

    @SerialName("DOTA_GAMERULES_STATE_CUSTOM_GAME_SETUP")
    CustomGameSetup,

}
