package com.dvt.greensys.weather.app.core.network.model

import kotlinx.serialization.Serializable


@Serializable
data class NetworkCoord(
    val lat: Double,
    val lon: Double,
)
