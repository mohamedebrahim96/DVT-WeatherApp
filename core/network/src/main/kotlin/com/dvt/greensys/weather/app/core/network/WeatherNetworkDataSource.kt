package com.co.greensys.weather.app.core.network

import com.co.greensys.weather.app.core.network.model.NetworkForecastData

interface WeatherNetworkDataSource {
    suspend fun fetchForecastByName(cityName: String): NetworkForecastData
    suspend fun fetchForecastByCoord(lat: Double, lon: Double): NetworkForecastData
}