package com.yanbin.geo.geoview

import org.junit.Assert
import org.junit.Test

class CollectionExtensionsTest {

    @Test
    fun `test pairwise`() {
        val list = listOf(1, 2, 3, 4)
        val expectResult = listOf(
            Pair(1, 2),
            Pair(2, 3),
            Pair(3, 4),
        )

        Assert.assertEquals(expectResult, list.pairewise())
    }
}