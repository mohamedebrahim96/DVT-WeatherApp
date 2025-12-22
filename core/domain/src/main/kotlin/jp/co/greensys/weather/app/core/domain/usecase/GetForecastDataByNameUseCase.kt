package jp.co.greensys.weather.app.core.domain.usecase

import jp.co.greensys.weather.app.core.common.result.Result
import jp.co.greensys.weather.app.core.common.result.runHandling
import jp.co.greensys.weather.app.core.domain.repository.ForecastRepository
import jp.co.greensys.weather.app.core.model.ForecastData
import javax.inject.Inject

class GetForecastDataByNameUseCase @Inject constructor(
    private val forecastRepository: ForecastRepository,
) {
    suspend operator fun invoke(cityName: String): Result<ForecastData> = runHandling {
        forecastRepository.getForecastDataByName(name = cityName)
    }
}
