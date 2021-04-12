package com.yanbin.geo.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.serialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.*

internal object ArcIndexSerializer: KSerializer<ArcIndex> {

    override fun deserialize(decoder: Decoder): ArcIndex {
        val jsonDecoder = decoder as JsonDecoder

        return parseArcsIndexes(
            jsonDecoder.decodeJsonElement()
        )
    }

    private fun parseArcsIndexes(jsonElement: JsonElement): ArcIndex {
        val jsonArray = jsonElement.jsonArray
        // is int
        return if (jsonArray[0] is JsonPrimitive) {
            ArcIntIndex(jsonArray.map { it.jsonPrimitive.int })
        } else {
            val childArcIndexes = jsonArray.map {
                parseArcsIndexes(
                    it
                )
            }
            ArcIndexList(childArcIndexes)
        }
    }

    override val descriptor: SerialDescriptor
        get() = serialDescriptor<ArcIndex>()

    override fun serialize(encoder: Encoder, value: ArcIndex) {
        throw UnsupportedOperationException("Do not need to serialize Arc in this project!")
    }
}