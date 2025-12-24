package com.dvt.greensys.weather.app.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkSnow(
    @SerialName("3h")
    val threeHour: Float,
)
