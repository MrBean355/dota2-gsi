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

package com.github.mrbean355.dota2.json

import com.github.mrbean355.dota2.Player
import com.github.mrbean355.dota2.Players
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

internal class PlayersDeserializer : JsonDeserializer<Players> {

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Players {
        val root = json.asJsonObject
        return Players(if (root.has("steamid")) {
            mapOf("player0" to context.deserialize(root, Player::class.java))
        } else {
            val players = mutableMapOf<String, Player>()
            root.entrySet().forEach { team ->
                team.value.asJsonObject.entrySet().forEach { player ->
                    players += player.key to context.deserialize(player.value, Player::class.java)
                }
            }
            players
        })
    }
}