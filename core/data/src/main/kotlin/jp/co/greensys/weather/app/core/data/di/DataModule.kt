package jp.co.greensys.weather.app.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.co.greensys.weather.app.core.data.repository.ForecastDataRepository
import jp.co.greensys.weather.app.core.domain.repository.ForecastRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    internal abstract fun bindsForecastRepository(
        forecastDataRepository: ForecastDataRepository,
    ): ForecastRepository
}
