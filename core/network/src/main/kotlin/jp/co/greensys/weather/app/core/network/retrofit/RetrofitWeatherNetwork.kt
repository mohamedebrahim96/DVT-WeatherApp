package jp.co.greensys.weather.app.core.network.retrofit

import jp.co.greensys.weather.app.core.network.BuildConfig
import jp.co.greensys.weather.app.core.network.WeatherNetworkDataSource
import jp.co.greensys.weather.app.core.network.model.NetworkForecastData
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

private interface RetrofitWeatherNetworkApi {
    // 都道府県名から天気予報
    @GET("data/2.5/forecast")
    suspend fun fetchForecastByName(
        @Query("q") cityName: String,
        @Query("appid") appid: String = BuildConfig.WEATHER_API_KEY,
        @Query("units") units: String? = "metric",
        @Query("lang") lang: String = "ja",
        // 8つ分の取得
        @Query("cnt") cnt: Int = 8,
    ): NetworkForecastData

    // 位置情報から天気予報
    @GET("data/2.5/forecast")
    suspend fun fetchForecastByCoord(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String = BuildConfig.WEATHER_API_KEY,
        @Query("units") units: String? = "metric",
        @Query("lang") lang: String = "en",
        // 8つ分の取得
        @Query("cnt") cnt: Int = 8,
    ): NetworkForecastData
}

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

    override suspend fun fetchForecastByName(cityName: String): NetworkForecastData =
        networkApi.fetchForecastByName(cityName = cityName)

    override suspend fun fetchForecastByCoord(lat: Double, lon: Double): NetworkForecastData =
        networkApi.fetchForecastByCoord(lat = lat, lon = lon)
}
