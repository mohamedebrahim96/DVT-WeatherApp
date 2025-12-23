package jp.co.greensys.weather.app.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 雪の情報
 *
 * @param threeHour 3時間の積雪量 (mm)
 */
@Serializable
data class NetworkSnow(
    @SerialName("3h")
    val threeHour: Float,
)
