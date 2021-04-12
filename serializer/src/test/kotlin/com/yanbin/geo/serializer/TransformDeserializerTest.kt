package com.yanbin.geo.serializer

import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class TransformDeserializerTest {

    @Test
    internal fun `deserialize transform`() {
        val data = """
            {
            "scale": [0.0005, 0.0001],
            "translate": [100, 0]
          }
        """
        val jsonElement = Json.parseToJsonElement(data)
        val transform = TransformDeserializer.deserialize(jsonElement)

        assertEquals(0.0005f, transform.scale.first)
        assertEquals(0.0001f, transform.scale.second)
        assertEquals(100f, transform.translate.first)
        assertEquals(0f, transform.translate.second)
    }

    @Test
    internal fun `deserialize transform with no translate should throw SerializationException`() {
        val data = """
            {
            "scale": [0.0005, 0.0001]
          }
        """
        assertThrows(SerializationException::class.java) {
            val jsonElement = Json.parseToJsonElement(data)
            TransformDeserializer.deserialize(jsonElement)
        }
    }

    @Test
    internal fun `deserialize transform with no scale should throw SerializationException`() {
        val data = """
            {
            "translate": [100, 0]
          }
        """
        assertThrows(SerializationException::class.java) {
            val jsonElement = Json.parseToJsonElement(data)
            TransformDeserializer.deserialize(jsonElement)
        }
    }
}