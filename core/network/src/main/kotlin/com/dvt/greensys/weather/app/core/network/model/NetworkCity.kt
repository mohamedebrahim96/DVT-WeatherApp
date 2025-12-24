package com.dvt.greensys.weather.app.core.network.model

import kotlinx.serialization.Serializable


@Serializable
data class NetworkCity(
    val coord: NetworkCoord,
    val country: String,
    val id: Int,
    val name: String,
    val population: Int,
    val sunrise: Int,
    val sunset: Int,
    val timezone: Int,
)
