package com.co.greensys.weather.app.core.domain.usecase

import com.co.greensys.weather.app.core.common.result.Result
import com.co.greensys.weather.app.core.common.result.runHandling
import com.co.greensys.weather.app.core.domain.repository.ForecastRepository
import com.co.greensys.weather.app.core.model.Coord
import com.co.greensys.weather.app.core.model.ForecastData
import javax.inject.Inject

class GetForecastDataByCoordUseCase @Inject constructor(
    private val forecastRepository: ForecastRepository,
) {
    suspend operator fun invoke(coord: Coord): Result<ForecastData> = runHandling {
        forecastRepository.getForecastDataByCoord(coord = coord)
    }
}
