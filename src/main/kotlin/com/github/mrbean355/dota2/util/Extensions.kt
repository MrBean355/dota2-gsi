package com.github.mrbean355.dota2.util

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer

internal inline fun <reified T> GsonBuilder.registerTypeAdapter(deserializer: JsonDeserializer<T>) {
    registerTypeAdapter(T::class.java, deserializer)
}