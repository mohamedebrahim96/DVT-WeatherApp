package com.dvt.greensys.weather.app.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 天気予報の内容
 *
 * @param clouds        曇りの状況
 * @param dt            時刻
 * @param dtTxt         時刻 文字列
 * @param main          温度・湿度・気圧などの情報
 * @param pop           降水確率 0 -> 0%, 1 -> 100%
 * @param rain          雨の情報
 * @param snow          雪の情報
 * @param sys           日中かどうか
 * @param visibility    平均視程
 * @param weather       天気の配列
 * @param wind          風向きなどの情報
 */
@Serializable
data class NetworkForecast(
    val clouds: NetworkFloatClouds,
    val dt: Long,
    @SerialName("dt_txt")
    val dtTxt: String,
    val main: NetworkMain,
    val pop: Float,
    val rain: NetworkRain? = null,
    val snow: NetworkSnow? = null,
    val sys: NetworkForecastSys,
    val visibility: Int,
    val weather: List<NetworkWeather>,
    val wind: NetworkWind,
)
