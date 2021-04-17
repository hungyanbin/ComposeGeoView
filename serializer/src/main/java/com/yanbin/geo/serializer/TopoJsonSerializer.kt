package com.yanbin.geo.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.serialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

internal object TopoJsonSerializer: KSerializer<TopoJSON> {

    override fun deserialize(decoder: Decoder): TopoJSON {
        val jsonDecoder = decoder as JsonDecoder
        val rootJsonObject = jsonDecoder.decodeJsonElement().jsonObject
        val type = rootJsonObject["type"]!!.jsonPrimitive.content
        val objects =
            parseRootGeoObject(
                rootJsonObject["objects"]!!.jsonObject
            )
        val arcs = ArcsDeserializer.deserialize(rootJsonObject["arcs"]!!)
        val transform: Transform? = rootJsonObject["transform"]?.let {
            TransformDeserializer.deserialize(it)
        }


        return TopoJSON(
            type,
            objects,
            arcs,
            transform
        )
    }

    private fun parseRootGeoObject(jsonObject: JsonObject): GeoObject {
        val rawGeoObject = jsonObject.toList()

        require(rawGeoObject.isNotEmpty())

        // TODO this assumption is wrong, can have more than one objects
        return GeoObjectDeserializer.deserialize(rawGeoObject[0].second)
    }

    override val descriptor: SerialDescriptor
        get() = serialDescriptor<TopoJSON>()

    override fun serialize(encoder: Encoder, value: TopoJSON) {
        throw UnsupportedOperationException("Do not need to serialize TopoJson in this project")
    }
}
