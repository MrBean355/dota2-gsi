package com.github.mrbean355.dota2

class SpectatingGameState(
        val provider: Provider,
        val map: DotaMap,
        val players: Collection<SpectatedPlayer>
)

class SpectatedPlayer(
        val playerInfo: Player,
        val hero: Hero,
        val abilities: Collection<Ability>,
        val items: HeroItems
)
