package jp.co.greensys.weather.app.core.network.model

import kotlinx.serialization.Serializable

/**
 * 天気予報のレスポンス
 *
 * @param city      都市情報
 * @param cnt       返されるタイムスタンプの数
 * @param list      天気予報の配列
 * @param cod       内部パラメタ
 * @param message   内部パラメタ
 *
 * ref: https://openweathermap.org/forecast5
 */
@Serializable
data class NetworkForecastData(
    val city: NetworkCity,
    val cnt: Int,
    val list: List<NetworkForecast>,
    val cod: String,
    val message: Int,
)
