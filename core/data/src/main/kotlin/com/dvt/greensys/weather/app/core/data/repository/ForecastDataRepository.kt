package com.dvt.greensys.weather.app.core.data.repository

import androidx.annotation.VisibleForTesting
import com.dvt.greensys.weather.app.core.domain.repository.ForecastRepository
import com.dvt.greensys.weather.app.core.network.WeatherNetworkDataSource
import javax.inject.Inject

// --- DOMAIN IMPORTS (The clean data your app uses) ---
// We import these normally because they are the "default" for this file.
import com.dvt.greensys.weather.app.core.model.City
import com.dvt.greensys.weather.app.core.model.Coord
import com.dvt.greensys.weather.app.core.model.ForecastData
import com.dvt.greensys.weather.app.core.model.ForecastItem
import com.dvt.greensys.weather.app.core.model.Main
import com.dvt.greensys.weather.app.core.model.Weather
import com.dvt.greensys.weather.app.core.model.Clouds
import com.dvt.greensys.weather.app.core.model.Wind
import com.dvt.greensys.weather.app.core.model.Sys

internal class ForecastDataRepository @Inject constructor(
    private val network: WeatherNetworkDataSource,
) : ForecastRepository {

    override suspend fun getForecastDataByCoord(coord: Coord): ForecastData {
        // We refer to the Network model by its FULL PATH to avoid any confusion
        val networkResponse: com.dvt.greensys.weather.app.core.network.model.ForecastData =
            network.fetchForecastByCoord(lat = coord.lat, lon = coord.lon)

        return networkResponse.toForecastData()
    }
}

// --- EXTENSION FUNCTION ---
// Maps Network Data -> Domain Data
@VisibleForTesting
fun com.dvt.greensys.weather.app.core.network.model.ForecastData.toForecastData(): ForecastData {

    // Explicitly map the list items using full path for input type
    val mappedList = this.list.map { item: com.dvt.greensys.weather.app.core.network.model.ForecastItem ->
        item.toDomainItem()
    }

    return ForecastData(
        cod = this.cod,
        message = this.message,
        cnt = this.cnt,
        list = mappedList,
        city = this.city.toDomainCity()
    )
}

// --- HELPER MAPPERS (Network -> Domain) ---

private fun com.dvt.greensys.weather.app.core.network.model.City.toDomainCity() = City(
    id = this.id,
    name = this.name,
    coord = this.coord.toDomainCoord(),
    country = this.country,
    population = this.population,
    timezone = this.timezone,
    sunrise = this.sunrise,
    sunset = this.sunset
)

private fun com.dvt.greensys.weather.app.core.network.model.Coord.toDomainCoord() = Coord(
    lat = this.lat,
    lon = this.lon
)

private fun com.dvt.greensys.weather.app.core.network.model.ForecastItem.toDomainItem() = ForecastItem(
    dt = this.dt,
    main = this.main.toDomainMain(),
    weather = this.weather.map { it.toDomainWeather() },
    clouds = this.clouds.toDomainClouds(),
    wind = this.wind.toDomainWind(),
    visibility = this.visibility,
    pop = this.pop.toDouble(), // Handle Float/Double conversion
    sys = this.sys.toDomainSys(),
    dt_txt = this.dt_txt
)

private fun com.dvt.greensys.weather.app.core.network.model.Main.toDomainMain() = Main(
    temp = this.temp,
    feelsLike = this.feelsLike,
    tempMin = this.tempMin,
    tempMax = this.tempMax,
    pressure = this.pressure,
    seaLevel = this.seaLevel,
    grndLevel = this.grndLevel,
    humidity = this.humidity,
    tempKf = this.tempKf
)

private fun com.dvt.greensys.weather.app.core.network.model.Weather.toDomainWeather() = Weather(
    id = this.id,
    main = this.main,
    description = this.description,
    icon = this.icon
)

private fun com.dvt.greensys.weather.app.core.network.model.Clouds.toDomainClouds() = Clouds(
    all = this.all
)

private fun com.dvt.greensys.weather.app.core.network.model.Wind.toDomainWind() = Wind(
    speed = this.speed,
    deg = this.deg,
    gust = this.gust
)

private fun com.dvt.greensys.weather.app.core.network.model.Sys.toDomainSys() = Sys(
    pod = this.pod
)