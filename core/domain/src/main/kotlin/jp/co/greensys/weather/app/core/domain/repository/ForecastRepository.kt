package jp.co.greensys.weather.app.core.domain.repository

import jp.co.greensys.weather.app.core.model.Coord
import jp.co.greensys.weather.app.core.model.ForecastData

interface ForecastRepository {
    suspend fun getForecastDataByName(name: String): ForecastData
    suspend fun getForecastDataByCoord(coord: Coord): ForecastData
}
