package com.github.mrbean355.dota2

class PlayingGameState(
        val provider: Provider,
        val map: DotaMap,
        val player: Player,
        val hero: Hero,
        val abilities: Collection<Ability>,
        val items: HeroItems
)