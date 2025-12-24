package com.dvt.greensys.weather.app.core.network.retrofit

import com.dvt.greensys.weather.app.core.network.BuildConfig
import com.dvt.greensys.weather.app.core.network.WeatherNetworkDataSource
import com.dvt.greensys.weather.app.core.network.model.ForecastData
import com.dvt.greensys.weather.app.core.network.model.NetworkForecastData
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.serialization.InternalSerializationApi // Import this

private interface RetrofitWeatherNetworkApi {

    @GET("data/2.5/forecast")
    suspend fun fetchForecastByCoord(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String = BuildConfig.WEATHER_API_KEY,
        @Query("units") units: String? = "metric",
        @Query("lang") lang: String = "en",
        // cnt removed to default to 40 timestamps (5 days)
    ): ForecastData
}


@OptIn(InternalSerializationApi::class)
@Singleton
internal class RetrofitWeatherNetwork @Inject constructor(
    networkJson: Json,
    okhttpCallFactory: dagger.Lazy<Call.Factory>,
) : WeatherNetworkDataSource {
    private val networkApi =
        Retrofit.Builder()
            .baseUrl(BuildConfig.WEATHER_API_BASE_URL)
            .callFactory { okhttpCallFactory.get().newCall(it) }
            .addConverterFactory(
                networkJson.asConverterFactory("application/json".toMediaType()),
            )
            .build()
            .create(RetrofitWeatherNetworkApi::class.java)


    override suspend fun fetchForecastByCoord(lat: Double, lon: Double): ForecastData =
        networkApi.fetchForecastByCoord(lat = lat, lon = lon)
}