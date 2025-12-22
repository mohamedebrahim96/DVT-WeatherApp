package jp.co.greensys.weather.app.core.network.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.co.greensys.weather.app.core.network.WeatherNetworkDataSource
import jp.co.greensys.weather.app.core.network.retrofit.RetrofitWeatherNetwork

@Module
@InstallIn(SingletonComponent::class)
internal interface DataSourceModule {
    @Binds
    fun binds(impl: RetrofitWeatherNetwork): WeatherNetworkDataSource
}
