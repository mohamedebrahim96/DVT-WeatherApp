package com.dvt.greensys.weather.app.core.domain.usecase

import com.dvt.greensys.weather.app.core.domain.repository.ForecastRepository
import javax.inject.Inject

class GetForecastDataByNameUseCase @Inject constructor(
    private val forecastRepository: ForecastRepository,
) {
//    suspend operator fun invoke(cityName: String): Result<ForecastData> = runHandling {
//        forecastRepository.getForecastDataByName(name = cityName)
//    }
}
