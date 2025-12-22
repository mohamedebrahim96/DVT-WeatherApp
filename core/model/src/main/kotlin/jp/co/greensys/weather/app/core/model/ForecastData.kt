package jp.co.greensys.weather.app.core.model

import kotlinx.datetime.Instant

data class ForecastData(
    val cityName: String,
    val date: Instant,
    val forecastList: List<Forecast>,
)
