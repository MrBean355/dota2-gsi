/*
 * Copyright 2023 Michael Johnston
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

package com.github.mrbean355.dota2.map

/**
 * Some general information about the map when the client is **spectating** the match.
 *
 * @see DotaMap
 */
interface SpectatedMap : DotaMap {
    val radiantWardPurchaseCooldown: Int
    val direWardPurchaseCooldown: Int
    val roshanState: String
    val roshanStateEndTimer: Int

    /** Probably requires Dota Plus and will be null if it's not owned. */
    val radiantWinChance: Int?
}
