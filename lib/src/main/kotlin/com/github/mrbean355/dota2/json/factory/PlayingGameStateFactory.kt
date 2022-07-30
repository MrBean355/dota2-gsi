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

package com.github.mrbean355.dota2.json.factory

import com.github.mrbean355.dota2.gamestate.PlayingGameState
import com.github.mrbean355.dota2.gamestate.PlayingGameStateImpl
import kotlinx.serialization.json.JsonObject

internal object PlayingGameStateFactory {

    fun create(root: JsonObject): PlayingGameState {
        return PlayingGameStateImpl(
            provider = ProviderFactory.create(root),
            map = MapFactory.createForPlayer(root),
            player = PlayerFactory.createForPlayer(root),
            hero = HeroFactory.createForPlayer(root),
            abilities = AbilitiesFactory.createForPlayer(root),
            items = ItemsFactory.createForPlayer(root),
            buildings = BuildingsFactory.createForPlayer(root),
            wearables = WearablesFactory.createForPlayer(root),
        )
    }
}