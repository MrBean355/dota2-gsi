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

package com.github.mrbean355.dota2.draft

import com.github.mrbean355.dota2.json.TeamDraftTransformer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class DraftImpl(
    @SerialName("activeteam") override val activeTeam: Int,
    @SerialName("pick") override val isPick: Boolean,
    @SerialName("activeteam_time_remaining") override val activeTeamTimeRemaining: Int,
    @SerialName("radiant_bonus_time") override val radiantBonusTime: Int,
    @SerialName("dire_bonus_time") override val direBonusTime: Int,
    @SerialName("team2") @Serializable(with = TeamDraftTransformer::class) override val radiantDraft: TeamDraftImpl,
    @SerialName("team3") @Serializable(with = TeamDraftTransformer::class) override val direDraft: TeamDraftImpl,
) : Draft
