package com.yanbin.geo.serializer

import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.float
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

internal object TransformDeserializer: JsonElementDeserializer<Transform> {

    override fun deserialize(jsonElement: JsonElement): Transform {
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
}
