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

import com.github.mrbean355.dota2.building.Building
import com.github.mrbean355.dota2.building.BuildingImpl
import com.github.mrbean355.dota2.testutil.jsonObject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class BuildingsFactoryTest {

    @Test
    internal fun create_KeyMissing_ReturnsNull() {
        val buildings = BuildingsFactory.create("empty.json".jsonObject)

        assertNull(buildings)
    }

    @Test
    internal fun create_EmptyObject_ReturnsEmptyList() {
        val buildings = BuildingsFactory.create("buildings_invalid.json".jsonObject)!!

        assertTrue(buildings.isEmpty())
    }

    @Test
    internal fun create_DeserializesObject() {
        val buildings = BuildingsFactory.create("buildings.json".jsonObject)!!

        assertEquals(34, buildings.size)

        with(buildings) {
            verify("dota_goodguys_tower1_top", 1795, 1800)
            verify("dota_goodguys_tower2_top", 2500, 2500)
            verify("dota_goodguys_tower3_top", 2500, 2500)
            verify("dota_goodguys_tower1_mid", 1018, 1800)
            verify("dota_goodguys_tower2_mid", 2500, 2500)
            verify("dota_goodguys_tower3_mid", 2500, 2500)
            verify("dota_goodguys_tower2_bot", 2192, 2500)
            verify("dota_goodguys_tower3_bot", 2500, 2500)
            verify("dota_goodguys_tower4_top", 2600, 2600)
            verify("dota_goodguys_tower4_bot", 2600, 2600)

            verify("good_rax_melee_top", 2200, 2200)
            verify("good_rax_range_top", 1300, 1300)
            verify("good_rax_melee_mid", 2200, 2200)
            verify("good_rax_range_mid", 1300, 1300)
            verify("good_rax_melee_bot", 2200, 2200)
            verify("good_rax_range_bot", 1300, 1300)

            verify("dota_goodguys_fort", 4500, 4500)

            verify("dota_badguys_tower2_top", 2427, 2500)
            verify("dota_badguys_tower3_top", 2500, 2500)
            verify("dota_badguys_tower1_mid", 1021, 1800)
            verify("dota_badguys_tower2_mid", 2500, 2500)
            verify("dota_badguys_tower3_mid", 2500, 2500)
            verify("dota_badguys_tower1_bot", 1340, 1800)
            verify("dota_badguys_tower2_bot", 2500, 2500)
            verify("dota_badguys_tower3_bot", 2500, 2500)
            verify("dota_badguys_tower4_top", 2600, 2600)
            verify("dota_badguys_tower4_bot", 2600, 2600)

            verify("bad_rax_melee_top", 2200, 2200)
            verify("bad_rax_range_top", 1300, 1300)
            verify("bad_rax_melee_mid", 2200, 2200)
            verify("bad_rax_range_mid", 1300, 1300)
            verify("bad_rax_melee_bot", 2200, 2200)
            verify("bad_rax_range_bot", 1300, 1300)

            verify("dota_badguys_fort", 4500, 4500)
        }
    }

    private fun Map<String, Building>.verify(id: String, health: Int, maxHealth: Int) {
        val instance = getValue(id) as BuildingImpl
        assertEquals(health, instance.health)
        assertEquals(maxHealth, instance.maxHealth)
    }
}