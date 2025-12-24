package com.dvt.greensys.weather.app.core.domain.usecase

import com.dvt.greensys.weather.app.core.common.result.Result
import com.dvt.greensys.weather.app.core.common.result.runHandling
import com.dvt.greensys.weather.app.core.domain.repository.ForecastRepository
import com.dvt.greensys.weather.app.core.model.Coord
import com.dvt.greensys.weather.app.core.model.ForecastData
import javax.inject.Inject

class GetForecastDataByCoordUseCase @Inject constructor(
    private val forecastRepository: ForecastRepository,
) {
    suspend operator fun invoke(coord: Coord): Result<ForecastData> = runHandling {
        forecastRepository.getForecastDataByCoord(coord = coord)
    }
}
