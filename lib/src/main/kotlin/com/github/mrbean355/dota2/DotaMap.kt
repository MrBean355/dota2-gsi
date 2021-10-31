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
