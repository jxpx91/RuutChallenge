package com.jp.ruutchallenge.model

data class SerieInfo(
    val date: String,
    val open: Pair<Double, Byte>,
    val high: Pair<Double, Byte>,
    val low: Pair<Double, Byte>,
    val close: Pair<Double, Byte>,
    val volume: Pair<Double, Byte>,
)
