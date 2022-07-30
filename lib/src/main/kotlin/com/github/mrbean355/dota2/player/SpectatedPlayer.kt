/*
 * Copyright 2022 Michael Johnston
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.mrbean355.dota2.player

/**
 * A player involved in the match when the client is **spectating** the match.
 * See [Player] for common properties.
 */
sealed interface SpectatedPlayer : Player {
    val id: String
    val netWorth: Int
    val heroDamage: Int
    val wardsPurchased: Int
    val wardsPlaced: Int
    val wardsDestroyed: Int
    val runesActivated: Int
    val campsStacked: Int
    val supportGoldSpent: Int
    val consumableGoldSpent: Int
    val itemGoldSpent: Int
    val goldLostToDeath: Int
    val goldSpentOnBuybacks: Int
}
