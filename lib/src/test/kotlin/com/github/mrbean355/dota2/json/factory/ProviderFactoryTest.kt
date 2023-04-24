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

import com.github.mrbean355.dota2.provider.ProviderImpl
import com.github.mrbean355.dota2.testutil.jsonObject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

internal class ProviderFactoryTest {

    @Test
    internal fun testCreate_KeyMissing_ReturnsNull() {
        val provider = ProviderFactory.create("empty.json".jsonObject)

        assertNull(provider)
    }

    @Test
    internal fun testCreate_DeserializesObject() {
        val provider = ProviderFactory.create("provider.json".jsonObject)!!

        with(provider as ProviderImpl) {
            assertEquals("Dota 2", name)
            assertEquals(570, appId)
            assertEquals(47, version)
            assertEquals(1658428769, timestamp)
        }
    }
}