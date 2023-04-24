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

import com.github.mrbean355.dota2.building.Building
import com.github.mrbean355.dota2.building.BuildingImpl
import com.github.mrbean355.dota2.testutil.jsonObject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class BuildingsFactoryTest {

    @Test
    internal fun createForPlayer_KeyMissing_ReturnsNull() {
        val buildings = BuildingsFactory.createForPlayer("empty.json".jsonObject)

        assertNull(buildings)
    }

    @Test
    internal fun createForPlayer_EmptyObject_ReturnsEmptyList() {
        val buildings = BuildingsFactory.createForPlayer("buildings_invalid.json".jsonObject)!!

        assertTrue(buildings.isEmpty())
    }

    @Test
    internal fun createForPlayer_DeserializesObject() {
        val buildings = BuildingsFactory.createForPlayer("buildings_playing.json".jsonObject)!!

        with(buildings) {
            assertEquals(17, size)

            verify(0, "dota_goodguys_tower1_top", 1795, 1800)
            verify(1, "dota_goodguys_tower2_top", 2500, 2500)
            verify(2, "dota_goodguys_tower3_top", 2500, 2500)
            verify(3, "dota_goodguys_tower1_mid", 1018, 1800)
            verify(4, "dota_goodguys_tower2_mid", 2500, 2500)
            verify(5, "dota_goodguys_tower3_mid", 2500, 2500)
            verify(6, "dota_goodguys_tower2_bot", 2192, 2500)
            verify(7, "dota_goodguys_tower3_bot", 2500, 2500)
            verify(8, "dota_goodguys_tower4_top", 2600, 2600)
            verify(9, "dota_goodguys_tower4_bot", 2600, 2600)

            verify(10, "good_rax_melee_top", 2200, 2200)
            verify(11, "good_rax_range_top", 1300, 1300)
            verify(12, "good_rax_melee_mid", 2200, 2200)
            verify(13, "good_rax_range_mid", 1300, 1300)
            verify(14, "good_rax_melee_bot", 2200, 2200)
            verify(15, "good_rax_range_bot", 1300, 1300)

            verify(16, "dota_goodguys_fort", 4500, 4500)
        }
    }

    @Test
    internal fun createForSpectator_KeyMissing_ReturnsNull() {
        val buildings = BuildingsFactory.createForSpectator("empty.json".jsonObject)

        assertNull(buildings)
    }

    @Test
    internal fun createForSpectator_EmptyObject_ReturnsEmptyList() {
        val buildings = BuildingsFactory.createForSpectator("buildings_invalid.json".jsonObject)!!

        assertTrue(buildings.isEmpty())
    }

    @Test
    internal fun createForSpectator_DeserializesObject() {
        val buildings = BuildingsFactory.createForSpectator("buildings_spectating.json".jsonObject)!!

        assertEquals(2, buildings.size)
        assertTrue("radiant" in buildings)
        assertTrue("dire" in buildings)

        with(buildings.getValue("radiant")) {
            assertEquals(17, size)

            verify(0, "dota_goodguys_tower1_top", 1795, 1800)
            verify(1, "dota_goodguys_tower2_top", 2500, 2500)
            verify(2, "dota_goodguys_tower3_top", 2500, 2500)
            verify(3, "dota_goodguys_tower1_mid", 1018, 1800)
            verify(4, "dota_goodguys_tower2_mid", 2500, 2500)
            verify(5, "dota_goodguys_tower3_mid", 2500, 2500)
            verify(6, "dota_goodguys_tower2_bot", 2192, 2500)
            verify(7, "dota_goodguys_tower3_bot", 2500, 2500)
            verify(8, "dota_goodguys_tower4_top", 2600, 2600)
            verify(9, "dota_goodguys_tower4_bot", 2600, 2600)

            verify(10, "good_rax_melee_top", 2200, 2200)
            verify(11, "good_rax_range_top", 1300, 1300)
            verify(12, "good_rax_melee_mid", 2200, 2200)
            verify(13, "good_rax_range_mid", 1300, 1300)
            verify(14, "good_rax_melee_bot", 2200, 2200)
            verify(15, "good_rax_range_bot", 1300, 1300)

            verify(16, "dota_goodguys_fort", 4500, 4500)
        }

        with(buildings.getValue("dire")) {
            assertEquals(17, size)

            verify(0, "dota_badguys_tower2_top", 2427, 2500)
            verify(1, "dota_badguys_tower3_top", 2500, 2500)
            verify(2, "dota_badguys_tower1_mid", 1021, 1800)
            verify(3, "dota_badguys_tower2_mid", 2500, 2500)
            verify(4, "dota_badguys_tower3_mid", 2500, 2500)
            verify(5, "dota_badguys_tower1_bot", 1340, 1800)
            verify(6, "dota_badguys_tower2_bot", 2500, 2500)
            verify(7, "dota_badguys_tower3_bot", 2500, 2500)
            verify(8, "dota_badguys_tower4_top", 2600, 2600)
            verify(9, "dota_badguys_tower4_bot", 2600, 2600)

            verify(10, "bad_rax_melee_top", 2200, 2200)
            verify(11, "bad_rax_range_top", 1300, 1300)
            verify(12, "bad_rax_melee_mid", 2200, 2200)
            verify(13, "bad_rax_range_mid", 1300, 1300)
            verify(14, "bad_rax_melee_bot", 2200, 2200)
            verify(15, "bad_rax_range_bot", 1300, 1300)

            verify(16, "dota_badguys_fort", 4500, 4500)
        }
    }

    private fun List<Building>.verify(index: Int, name: String, health: Int, maxHealth: Int) {
        val instance = get(index) as BuildingImpl
        assertEquals(name, instance.name)
        assertEquals(health, instance.health)
        assertEquals(maxHealth, instance.maxHealth)
    }
}