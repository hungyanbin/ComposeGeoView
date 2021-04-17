package com.yanbin.geo.serializer

import kotlinx.serialization.json.JsonElement

interface JsonElementDeserializer<T> {

    fun deserialize(jsonElement: JsonElement): T
}
