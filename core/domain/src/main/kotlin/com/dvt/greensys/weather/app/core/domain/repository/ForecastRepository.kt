package com.dvt.greensys.weather.app.core.domain.repository

import com.dvt.greensys.weather.app.core.model.Coord
import com.dvt.greensys.weather.app.core.model.ForecastData

interface ForecastRepository {
    suspend fun getForecastDataByCoord(coord: Coord): ForecastData
}