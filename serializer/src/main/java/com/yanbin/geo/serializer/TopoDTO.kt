package com.yanbin.geo.serializer

import kotlinx.serialization.Serializable

@Serializable(with = ArcsSerializer::class)
sealed class Arc

class MultiArc(val arcs: List<Arc>):
    Arc()
class PositionArc(val x: Int, val y: Int):
    Arc()