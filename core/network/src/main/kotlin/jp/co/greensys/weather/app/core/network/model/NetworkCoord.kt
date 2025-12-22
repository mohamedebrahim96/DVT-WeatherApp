package jp.co.greensys.weather.app.core.network.model

import kotlinx.serialization.Serializable

/**
 * 座標
 *
 * @param lat   緯度
 * @param lon   軽度
 */
@Serializable
data class NetworkCoord(
    val lat: Double,
    val lon: Double,
)
