package com.github.mrbean355.dota2.json.transform

import com.github.mrbean355.dota2.player.SpectatedPlayer
import com.github.mrbean355.dota2.player.SpectatedPlayerImpl
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.JsonTransformingSerializer
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.jsonObject

internal object SpectatedPlayerTransformer : JsonTransformingSerializer<List<SpectatedPlayerImpl>>(ListSerializer(SpectatedPlayerImpl.serializer())) {

    override fun transformDeserialize(element: JsonElement): JsonElement {
        return buildJsonArray {
            element.jsonObject.forEach { (playerId, data) ->
                add(
                    JsonObject(data.jsonObject.plus(SpectatedPlayer::id.name to JsonPrimitive(playerId)))
                )
            }
        }
    }
}