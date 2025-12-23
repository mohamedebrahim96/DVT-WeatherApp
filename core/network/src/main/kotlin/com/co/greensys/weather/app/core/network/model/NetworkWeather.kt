package jp.co.greensys.weather.app.core.network.model

import kotlinx.serialization.Serializable

/**
 * 天気
 *
 * @param description   天気説明
 * @param icon          天気アイコンid
 * @param id            天気id
 * @param main          天気グループ
 */
@Serializable
data class NetworkWeather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String,
)
