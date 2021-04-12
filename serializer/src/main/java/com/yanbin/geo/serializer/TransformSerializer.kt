package com.yanbin.geo.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.serialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.*

internal object TransformSerializer: KSerializer<Transform> {

    override fun deserialize(decoder: Decoder): Transform {
        val jsonDecoder = decoder as JsonDecoder

        return parseTransform(jsonDecoder.decodeJsonElement())
    }

    private fun parseTransform(jsonElement: JsonElement): Transform {
        val rawTransform = jsonElement.jsonObject
        if (!rawTransform.containsKey("scale") || !rawTransform.containsKey("translate")) {
            throw SerializationException("transform must contains both 'scale' and 'translate'")
        }

        val rawScale = rawTransform["scale"]!!.jsonArray
        val rawTranslate = rawTransform["translate"]!!.jsonArray

        val scale = Pair(rawScale[0].jsonPrimitive.float, rawScale[1].jsonPrimitive.float)
        val translate = Pair(rawTranslate[0].jsonPrimitive.float, rawTranslate[1].jsonPrimitive.float)

        return Transform(scale, translate)
    }


    override val descriptor: SerialDescriptor
        get() = serialDescriptor<Transform>()

    override fun serialize(encoder: Encoder, value: Transform) {
        throw UnsupportedOperationException("Do not need to serialize Arc in this project!")
    }
}