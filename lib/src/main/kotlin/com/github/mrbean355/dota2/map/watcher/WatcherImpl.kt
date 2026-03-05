package com.github.mrbean355.dota2.map.watcher

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class WatcherImpl(
    @SerialName("location_x") override val locationX: Int,
    @SerialName("location_y") override val locationY: Int,
    @SerialName("capture_state") override val captureState: String
) : Watcher
