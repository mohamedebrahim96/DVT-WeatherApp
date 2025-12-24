package com.dvt.greensys.weather.app.core.network.model

import kotlinx.serialization.Serializable

/**
 * 日中かどうか
 *
 * @param pod   n -> night, d -> day
 */
@Serializable
data class NetworkForecastSys(
    val pod: String,
)
