package com.co.greensys.weather.app.core.model

import kotlinx.datetime.Instant

data class Forecast(
    val date: Instant,
    val maxTemp: Float,
    val minTemp: Float,
    val humidity: Int,
    val iconId: String,
    private val pop: Float,
) {
    val popPercent: Int
        get() = (pop * 100).toInt()
}
