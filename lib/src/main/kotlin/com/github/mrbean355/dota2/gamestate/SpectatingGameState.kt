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

package com.github.mrbean355.dota2.gamestate

import com.github.mrbean355.dota2.ability.Ability
import com.github.mrbean355.dota2.building.Building
import com.github.mrbean355.dota2.draft.Draft
import com.github.mrbean355.dota2.hero.SpectatedHero
import com.github.mrbean355.dota2.item.Items
import com.github.mrbean355.dota2.map.SpectatedMap
import com.github.mrbean355.dota2.player.SpectatedPlayer
import com.github.mrbean355.dota2.provider.Provider

/**
 * The state of the match at a point in time, when the client is **spectating** the match.
 */
interface SpectatingGameState : GameState {

    val provider: Provider?

    val map: SpectatedMap?

    /** Map of team name to their players. */
    val players: Map<String, List<SpectatedPlayer>>?

    /** Map of player ID to their hero. */
    val heroes: Map<String, SpectatedHero>?

    /** Map of player ID to their hero's abilities. */
    val abilities: Map<String, List<Ability>>?

    /** Map of player ID to their hero's items. */
    val items: Map<String, Items>?

    /** Map of team name to their buildings. */
    val buildings: Map<String, List<Building>>?

    /** Map of player ID to their hero's wearable IDs. */
    val wearables: Map<String, List<Int>>?

    val draft: Draft?

}
