package com.dvt.greensys.weather.app.core.data.repository

import com.dvt.greensys.weather.app.core.model.Forecast
import com.dvt.greensys.weather.app.core.model.ForecastData
import com.dvt.greensys.weather.app.core.network.model.NetworkCity
import com.dvt.greensys.weather.app.core.network.model.NetworkCoord
import com.dvt.greensys.weather.app.core.network.model.NetworkFloatClouds
import com.dvt.greensys.weather.app.core.network.model.NetworkForecast
import com.dvt.greensys.weather.app.core.network.model.NetworkForecastData
import com.dvt.greensys.weather.app.core.network.model.NetworkForecastSys
import com.dvt.greensys.weather.app.core.network.model.NetworkMain
import com.dvt.greensys.weather.app.core.network.model.NetworkWeather
import com.dvt.greensys.weather.app.core.network.model.NetworkWind
import kotlinx.datetime.Instant
import kotlin.test.Test
import kotlin.test.assertEquals

class ForecastDataRepositoryKtTest {
    @Test
    fun toForecastData_test() {
        val networkForecastData = NetworkForecastData(
            city = NetworkCity(
                coord = NetworkCoord(
                    lat = 35.6895,
                    lon = 139.6917,
                ),
                country = "JP",
                id = 1850144,
                name = "東京都",
                population = 12445327,
                sunrise = 1725740318,
                sunset = 1725785978,
                timezone = 32400,
            ),
            cnt = 8,
            list = listOf(
                NetworkForecast(
                    clouds = NetworkFloatClouds(all = 35),
                    dt = 1725775200,
                    dtTxt = "2024-09-08 06:00:00",
                    main = NetworkMain(
                        feelsLike = 38.5f,
                        grndLevel = 1007,
                        humidity = 58,
                        pressure = 1012,
                        seaLevel = 1012,
                        temp = 32.87f,
                        tempKf = 0.85f,
                        tempMax = 32.87f,
                        tempMin = 32.02f,
                    ),
                    pop = 0.16f,
                    rain = null,
                    snow = null,
                    sys = NetworkForecastSys(pod = "d"),
                    visibility = 10000,
                    weather = listOf(
                        NetworkWeather(
                            description = "雲",
                            icon = "03d",
                            id = 802,
                            main = "Clouds",
                        ),
                    ),
                    wind = NetworkWind(
                        deg = 177,
                        gust = 4.96f,
                        speed = 5.98f,
                    ),
                ),
            ),
            cod = "200",
            message = 0,
        )
        val expected = ForecastData(
            cityName = "東京都",
            date = Instant.fromEpochSeconds(epochSeconds = 1725775200),
            forecastList = listOf(
                Forecast(
                    date = Instant.fromEpochSeconds(epochSeconds = 1725775200),
                    maxTemp = 32.87f,
                    minTemp = 32.02f,
                    humidity = 58,
                    iconId = "03d",
                    pop = 0.16f,
                ),
            ),
        )

        val result = networkForecastData.toForecastData()
        assertEquals(expected, result)
    }
}
