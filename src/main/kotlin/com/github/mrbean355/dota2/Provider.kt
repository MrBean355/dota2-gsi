package com.github.mrbean355.dota2

import com.google.gson.annotations.SerializedName

data class Provider(

        @SerializedName("name")
        val name: String,

        @SerializedName("appid")
        val appId: Int,

        @SerializedName("version")
        val version: Int,

        @SerializedName("timestamp")
        val timestamp: Long

)
