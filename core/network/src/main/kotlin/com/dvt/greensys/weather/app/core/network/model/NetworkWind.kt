package com.dvt.greensys.weather.app.core.network.model

import kotlinx.serialization.Serializable

/**
 * 風向きなどの情報
 *
 * @param deg   風向
 * @param gust  突風 (m/s)
 * @param speed 風速 (m/s)
 */
@Serializable
data class NetworkWind(
    val deg: Int,
    val gust: Float,
    val speed: Float,
)
