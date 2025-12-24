package com.dvt.greensys.weather.app.core.network

import com.dvt.greensys.weather.app.core.network.model.ForecastData
import com.dvt.greensys.weather.app.core.network.model.NetworkForecastData

interface WeatherNetworkDataSource {

    suspend fun fetchForecastByCoord(lat: Double, lon: Double): ForecastData
}