package com.dvt.greensys.weather.app.core.network

import com.dvt.greensys.weather.app.core.network.model.NetworkForecastData

interface WeatherNetworkDataSource {
    suspend fun fetchForecastByName(cityName: String): NetworkForecastData
    suspend fun fetchForecastByCoord(lat: Double, lon: Double): NetworkForecastData
}