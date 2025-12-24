package com.dvt.greensys.weather.app.core.network.model

import kotlinx.serialization.Serializable

/**
 * 曇りの状況
 *
 * @param all   曇り状況 (%)
 */
@Serializable
data class NetworkFloatClouds(
    val all: Int,
)
