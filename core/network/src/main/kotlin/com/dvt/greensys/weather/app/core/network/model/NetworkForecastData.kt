package com.dvt.greensys.weather.app.core.network.model

import kotlinx.serialization.Serializable


@Serializable
data class NetworkForecastData(
    val city: NetworkCity,
    val cnt: Int,
    val list: List<NetworkForecast>,
    val cod: String,
    val message: Int,
)
