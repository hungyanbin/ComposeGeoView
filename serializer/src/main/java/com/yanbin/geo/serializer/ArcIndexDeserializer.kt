package com.yanbin.geo.serializer

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonPrimitive

internal object ArcIndexDeserializer: JsonElementDeserializer<ArcIndex> {

    override fun deserialize(jsonElement: JsonElement): ArcIndex {
        val jsonArray = jsonElement.jsonArray
        // is int
        return if (jsonArray[0] is JsonPrimitive) {
            ArcIntIndex(jsonArray.map { it.jsonPrimitive.int })
        } else {
            val childArcIndexes = jsonArray.map {
                deserialize(
                    it
                )
            }
            ArcIndexList(childArcIndexes)
        }
    }

}
