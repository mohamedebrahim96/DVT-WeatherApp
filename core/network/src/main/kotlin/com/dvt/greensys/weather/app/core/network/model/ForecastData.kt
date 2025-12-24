package com.dvt.greensys.weather.app.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ForecastData(
    val cod: String,
    val message: Int,
    val cnt: Int,
    val list: List<ForecastItem>,
    val city: City // This must refer to the City class in this package (defined below or in separate file)
)



@Serializable
data class ForecastItem(
    val dt: Long,
    val main: Main,
    val weather: List<Weather>,
    val clouds: Clouds,
    val wind: Wind,
    val visibility: Int,
    val pop: Double,
    val sys: Sys,
    @SerialName("dt_txt")
    val dt_txt: String
)

@Serializable
data class Main(
    val temp: Double,
    @SerialName("feels_like")
    val feelsLike: Double,
    @SerialName("temp_min")
    val tempMin: Double,
    @SerialName("temp_max")
    val tempMax: Double,
    val pressure: Int,
    @SerialName("sea_level")
    val seaLevel: Int,
    @SerialName("grnd_level")
    val grndLevel: Int,
    val humidity: Int,
    @SerialName("temp_kf")
    val tempKf: Double
)

@Serializable
data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

@Serializable
data class Clouds(
    val all: Int
)

@Serializable
data class Wind(
    val speed: Double,
    val deg: Int,
    val gust: Double
)

@Serializable
data class Sys(
    val pod: String
)