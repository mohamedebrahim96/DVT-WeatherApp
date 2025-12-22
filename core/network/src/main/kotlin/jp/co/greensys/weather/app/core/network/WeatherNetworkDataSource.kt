package jp.co.greensys.weather.app.core.network

import jp.co.greensys.weather.app.core.network.model.NetworkForecastData

interface WeatherNetworkDataSource {
    suspend fun fetchForecastByName(cityName: String): NetworkForecastData
    suspend fun fetchForecastByCoord(lat: Double, lon: Double): NetworkForecastData
}
