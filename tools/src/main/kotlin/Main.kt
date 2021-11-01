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

package com.github.mrbean355.dota2.tools

import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.JsonPrimitive
import java.io.File

/**
 * Normalises the [Inputs] JSON files; replacing all values with static defaults.
 * For example, string values will be replaced with "string".
 * This makes it easier to compare JSON blobs from Dota 2 GSI, to see what's changed
 * in the JSON structure after Dota updates.
 */
fun main() {
    Inputs.forEach {
        val f = File(it)
        f.writeText(f.normaliseContent())
        println("Wrote: $it (${f.length()} bytes)")
    }
}

private val Inputs = listOf(
    "tools/src/main/resources/empty.json",
    "tools/src/main/resources/playing.json",
    "tools/src/main/resources/spectating.json"
)

private val gson = GsonBuilder().setPrettyPrinting().create()

private fun File.normaliseContent(): String {
    check(exists())
    val json = JsonParser.parseString(readText())
    val normalised = json.normalise() as JsonObject
    normalised.remove("previously")
    return gson.toJson(normalised)
}

private fun JsonElement.normalise(): JsonElement {
    return when (this) {
        is JsonPrimitive -> {
            val p = asJsonPrimitive
            when {
                p.isBoolean -> JsonPrimitive(false)
                p.isNumber -> JsonPrimitive(0)
                p.isString -> JsonPrimitive("string")
                else -> error("Unexpected primitive type: $p")
            }
        }
        is JsonObject -> {
            val obj = JsonObject()
            asJsonObject.entrySet().forEach { (k, v) ->
                obj.add(k, v.normalise())
            }
            obj
        }
        else -> error("Unexpected element type: ${this::class}")
    }
}