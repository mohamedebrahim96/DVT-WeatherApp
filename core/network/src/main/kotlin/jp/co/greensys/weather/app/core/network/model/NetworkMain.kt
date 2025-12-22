package jp.co.greensys.weather.app.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 温度・湿度・気圧などの情報
 *
 * @param feelsLike 体感温度
 * @param grndLevel 地上気圧 (hPa)
 * @param humidity  湿度 (%)
 * @param pressure  デフォルトの海面気圧 (hPa)
 * @param seaLevel  海面気圧 (hPa)
 * @param temp      気温
 * @param tempKf    内部パラメータ
 * @param tempMax   最高気温
 * @param tempMin   最低気温
 */
@Serializable
data class NetworkMain(
    @SerialName("feels_like")
    val feelsLike: Float,
    @SerialName("grnd_level")
    val grndLevel: Int,
    val humidity: Int,
    val pressure: Int,
    @SerialName("sea_level")
    val seaLevel: Int,
    val temp: Float,
    @SerialName("temp_kf")
    val tempKf: Float,
    @SerialName("temp_max")
    val tempMax: Float,
    @SerialName("temp_min")
    val tempMin: Float,
)
