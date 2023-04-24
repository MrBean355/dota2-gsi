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

package com.github.mrbean355.dota2.item

/**
 * A collection of item slots that belong to a hero.
 */
data class Items(
    /** Item slots on the hero, including backpack. */
    val inventory: List<Item>,
    /** Item slots for the hero's stash, at their fountain. */
    val stash: List<Item>,
    /** Item slot for the hero's teleport scroll. */
    val teleport: Item,
    /** Item slot for the hero's neutral item. */
    val neutral: Item,
)
