package com.yanbin.geo.core

data class PathF (private val points: List<PointF>) {

    operator fun get(index: Int): PointF {
        return points[index]
    }
}