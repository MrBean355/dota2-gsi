package com.github.mrbean355.dota2.json.transform

import com.github.mrbean355.dota2.building.Building
import com.github.mrbean355.dota2.building.BuildingImpl
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.JsonTransformingSerializer
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.jsonObject

/**
 * Converts an object representing a team's buildings:
 * ```
 * {
 *   "dota_goodguys_tower1_top": {
 *     "health": 1795,
 *     "max_health": 1800
 *   },
 *   "dota_goodguys_tower2_top": {
 *     "health": 2500,
 *     "max_health": 2500
 *   }
 * }
 * ```
 * Into an array:
 * ```
 * [
 *   {
 *     "name": "dota_goodguys_tower1_top",
 *     "health": 1795,
 *     "max_health": 1800,
 *   },
 *   {
 *     "name": "dota_goodguys_tower2_top",
 *     "health": 2500,
 *     "max_health": 2500,
 *   }
 * ]
 * ```
 */
internal object BuildingsTransformer : JsonTransformingSerializer<List<BuildingImpl>>(ListSerializer(BuildingImpl.serializer())) {

    override fun transformDeserialize(element: JsonElement): JsonElement {
        return buildJsonArray {
            element.jsonObject.forEach { (buildingName, data) ->
                add(
                    JsonObject(data.jsonObject.plus(Building::name.name to JsonPrimitive(buildingName)))
                )
            }
        }
    }
}
