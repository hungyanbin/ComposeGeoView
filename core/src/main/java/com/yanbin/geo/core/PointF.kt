package com.yanbin.geo.core

data class PointF(val x: Float, val y: Float) {
    companion object {
        val DEFAULT = PointF(0f, 0f)
    }
}