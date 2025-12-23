package jp.co.greensys.weather.app.core.network.model

import kotlinx.serialization.Serializable

/**
 * 都市情報
 *
 * @param coord         座標
 * @param country       都市コード
 * @param id            都市id
 * @param name          都市名
 * @param population    都市人口
 * @param sunrise       日の出
 * @param sunset        日の入
 * @param timezone      UTC からの秒シフト
 */
@Serializable
data class NetworkCity(
    val coord: NetworkCoord,
    val country: String,
    val id: Int,
    val name: String,
    val population: Int,
    val sunrise: Int,
    val sunset: Int,
    val timezone: Int,
)
