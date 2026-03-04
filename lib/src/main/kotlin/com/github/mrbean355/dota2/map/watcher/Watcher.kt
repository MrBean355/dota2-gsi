package com.github.mrbean355.dota2.map.watcher

/**
 * A Watcher on the map which can be captured by a team.
 */
interface Watcher {
    val locationX: Int
    val locationY: Int
    val captureState: String
}
