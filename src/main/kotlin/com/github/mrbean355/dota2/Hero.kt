package com.github.mrbean355.dota2

import com.google.gson.annotations.SerializedName

data class Hero(

    @SerializedName("xpos")
    val xPos: Long,

    @SerializedName("ypos")
    val yPos: Long,

    @SerializedName("id")
    val id: Long,

    @SerializedName("name")
    val name: String,

    @SerializedName("level")
    val level: Long,

    @SerializedName("alive")
    val alive: Boolean,

    @SerializedName("respawn_seconds")
    val respawnSeconds: Long,

    @SerializedName("buyback_cost")
    val buybackCost: Long,

    @SerializedName("buyback_cooldown")
    val buybackCooldown: Long,

    @SerializedName("health")
    val health: Long,

    @SerializedName("max_health")
    val maxHealth: Long,

    @SerializedName("health_percent")
    val healthPercent: Long,

    @SerializedName("mana")
    val mana: Long,

    @SerializedName("max_mana")
    val maxMana: Long,

    @SerializedName("mana_percent")
    val manaPercent: Long,

    @SerializedName("silenced")
    val silenced: Boolean,

    @SerializedName("stunned")
    val stunned: Boolean,

    @SerializedName("disarmed")
    val disarmed: Boolean,

    @SerializedName("magicimmune")
    val magicImmune: Boolean,

    @SerializedName("hexed")
    val hexed: Boolean,

    @SerializedName("muted")
    val muted: Boolean,

    @SerializedName("break")
    val `break`: Boolean,

    @SerializedName("smoked")
    val smoked: Boolean,

    @SerializedName("has_debuff")
    val hasDebuff: Boolean,

    @SerializedName("talent_1")
    val talent1: Boolean,

    @SerializedName("talent_2")
    val talent2: Boolean,

    @SerializedName("talent_3")
    val talent3: Boolean,

    @SerializedName("talent_4")
    val talent4: Boolean,

    @SerializedName("talent_5")
    val talent5: Boolean,

    @SerializedName("talent_6")
    val talent6: Boolean,

    @SerializedName("talent_7")
    val talent7: Boolean,

    @SerializedName("talent_8")
    val talent8: Boolean,

    // BEGIN: spectating

    @SerializedName("selected_unit")
    val selectedUnit: Boolean?

)

class Heroes internal constructor(c: Map<String, Hero>) : HashMap<String, Hero>(c)