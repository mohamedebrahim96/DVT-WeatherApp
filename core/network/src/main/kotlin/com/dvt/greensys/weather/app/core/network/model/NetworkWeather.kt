package com.dvt.greensys.weather.app.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkWeather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String,
)
