package com.yanbin.geo.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.serialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.*

object ArcsSerializer: KSerializer<Arc> {

    override fun deserialize(decoder: Decoder): Arc {
        val jsonDecoder = decoder as JsonDecoder

        return parseArcs(
            jsonDecoder.decodeJsonElement().jsonArray
        )
    }

    private fun parseArcs(jsonElement: JsonElement): Arc {
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
                parseArcs(
                    it
                )
            }
            MultiArc(childArcs)
        }
    }

    override val descriptor: SerialDescriptor
        get() = serialDescriptor<Arc>()

    override fun serialize(encoder: Encoder, value: Arc) {
        throw UnsupportedOperationException("Do not need to serialize Arc in this project!")
    }
}