package com.yanbin.geo.serializer

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonPrimitive

internal object ArcsDeserializer: JsonElementDeserializer<Arc> {

    override fun deserialize(jsonElement: JsonElement): Arc {
        val jsonArray = jsonElement.jsonArray
        // is position
        return if (jsonArray[0] is JsonPrimitive) {
            require(jsonArray.size == 2)
            PositionArc(
                jsonArray[0].jsonPrimitive.int,
                jsonArray[1].jsonPrimitive.int
            )
        } else {
            val childArcs = jsonArray.map {
                deserialize(
                    it
                )
            }
            MultiArc(childArcs)
        }
    }
}
