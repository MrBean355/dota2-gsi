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

package com.github.mrbean355.dota2.json.factory

import com.github.mrbean355.dota2.gamestate.IdleGameState
import com.github.mrbean355.dota2.gamestate.IdleGameStateImpl
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject

internal object IdleGameStateFactory {

    fun create(root: JsonObject, json: Json): IdleGameState {
        return IdleGameStateImpl(
            provider = ProviderFactory.create(root, json)
        )
    }
}