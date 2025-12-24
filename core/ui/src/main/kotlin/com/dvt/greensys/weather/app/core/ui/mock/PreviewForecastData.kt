package com.dvt.greensys.weather.app.core.ui.mock

import com.dvt.greensys.weather.app.core.model.*

object PreviewForecastData {
    // 1. Create a dummy City
    private val dummyCity = City(
        id = 1,
        name = "Dubai",
        coord = Coord(lat = 25.2, lon = 55.2),
        country = "AE",
        population = 3000000,
        timezone = 14400,
        sunrise = 1600000000L,
        sunset = 1600040000L
    )

    // 2. Create dummy List items
    private fun createDummyItem(dt: Long, temp: Double, icon: String): ForecastItem {
        return ForecastItem(
            dt = dt,
            main = Main(
                temp = temp,
                feelsLike = temp + 2,
                tempMin = temp - 2,
                tempMax = temp + 5, // Used in UI
                pressure = 1012,
                seaLevel = 1012,
                grndLevel = 1000,
                humidity = 40, // Used in UI
                tempKf = 0.0
            ),
            weather = listOf(
                Weather(id = 800, main = "Clear", description = "clear sky", icon = icon)
            ),
            clouds = Clouds(all = 0),
            wind = Wind(speed = 5.0, deg = 120, gust = 7.0),
            visibility = 10000,
            pop = 0.0,
            sys = Sys(pod = "d"),
            dt_txt = "2025-01-01 12:00:00"
        )
    }

    // 3. Assemble the full ForecastData object
    val default = ForecastData(
        cod = "200",
        message = 0,
        cnt = 5,
        list = listOf(
            createDummyItem(1700000000L, 25.0, "01d"),
            createDummyItem(1700010800L, 27.0, "02d"),
            createDummyItem(1700021600L, 22.0, "03d"),
            createDummyItem(1700032400L, 21.0, "04d"),
            createDummyItem(1700043200L, 20.0, "09d")
        ),
        city = dummyCity
    )
}