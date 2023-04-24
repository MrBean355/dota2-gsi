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

import com.github.mrbean355.dota2.testutil.jsonObject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class WearablesFactoryTest {

    @Test
    internal fun createForPlayer_KeyMissing_ReturnsNull() {
        val wearables = WearablesFactory.createForPlayer("empty.json".jsonObject)

        assertNull(wearables)
    }

    @Test
    internal fun createForPlayer_EmptyObject_ReturnsEmptyList() {
        val wearables = WearablesFactory.createForPlayer("wearables_invalid.json".jsonObject)!!

        assertTrue(wearables.isEmpty())
    }

    @Test
    internal fun createForPlayer_DeserializesObject() {
        val wearables = WearablesFactory.createForPlayer("wearables_playing.json".jsonObject)!!

        assertEquals(listOf(9232, 14954, 13914, 13911, 9668, 550, 790, 792, 14912), wearables)
    }

    @Test
    internal fun createForSpectator_KeyMissing_ReturnsNull() {
        val wearables = WearablesFactory.createForSpectator("empty.json".jsonObject)

        assertNull(wearables)
    }

    @Test
    internal fun createForSpectator_EmptyObject_ReturnsEmptyList() {
        val wearables = WearablesFactory.createForSpectator("wearables_invalid.json".jsonObject)!!

        assertTrue(wearables.isEmpty())
    }

    @Test
    internal fun createForSpectator_DeserializesObject() {
        val wearables = WearablesFactory.createForSpectator("wearables_spectating.json".jsonObject)!!

        assertEquals(10, wearables.size)
        assertEquals(listOf(19187, 17951, 19190, 7466, 19186, 8632, 724, 792, 793, 14912), wearables["player5"])
    }
}