package com.dvt.greensys.weather.app.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 雨の情報
 *
 * @param threeHour 3時間の雨量 (mm)
 */
@Serializable
data class NetworkRain(
    @SerialName("3h")
    val threeHour: Float,
)
