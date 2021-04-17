package com.yanbin.geo.geoview

fun <T> List<T>.pairewise(): List<Pair<T, T>> {
    if (this.size < 2) return emptyList()

    return this.dropLast(1).zip(this.drop(1))
}
