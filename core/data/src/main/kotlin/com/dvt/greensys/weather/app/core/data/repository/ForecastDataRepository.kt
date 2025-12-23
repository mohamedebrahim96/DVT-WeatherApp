package com.co.greensys.weather.app.core.data.repository

import androidx.annotation.VisibleForTesting
import com.co.greensys.weather.app.core.domain.repository.ForecastRepository
import com.co.greensys.weather.app.core.model.Coord
import com.co.greensys.weather.app.core.model.Forecast
import com.co.greensys.weather.app.core.model.ForecastData
import com.co.greensys.weather.app.core.network.WeatherNetworkDataSource
import com.co.greensys.weather.app.core.network.model.NetworkForecast
import com.co.greensys.weather.app.core.network.model.NetworkForecastData
import kotlinx.datetime.Instant
import javax.inject.Inject

internal class ForecastDataRepository @Inject constructor(
    private val network: WeatherNetworkDataSource,
) : ForecastRepository {
    override suspend fun getForecastDataByName(name: String): ForecastData =
        network.fetchForecastByName(cityName = name).toForecastData()

    override suspend fun getForecastDataByCoord(coord: Coord): ForecastData =
        network.fetchForecastByCoord(lat = coord.lat, lon = coord.lon).toForecastData()
}

@VisibleForTesting
fun NetworkForecastData.toForecastData() = ForecastData(
    cityName = city.name,
    date = Instant.fromEpochSeconds(epochSeconds = list.first().dt),
    forecastList = list.map(NetworkForecast::toForecast),
)

private fun NetworkForecast.toForecast() = Forecast(
    date = Instant.fromEpochSeconds(epochSeconds = dt),
    maxTemp = main.tempMax,
    minTemp = main.tempMin,
    humidity = main.humidity,
    iconId = weather.first().icon,
    pop = pop,
)
