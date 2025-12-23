package com.co.greensys.weather.app.core.domain

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import com.co.greensys.weather.app.core.common.result.Result
import com.co.greensys.weather.app.core.domain.repository.ForecastRepository
import com.co.greensys.weather.app.core.domain.usecase.GetForecastDataByCoordUseCase
import com.co.greensys.weather.app.core.model.Coord
import com.co.greensys.weather.app.core.model.ForecastData
import com.co.greensys.weather.app.core.testing.util.MainDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import kotlin.test.Test
import kotlin.test.assertIs

private const val LON = 139.6917
private const val LAT = 35.6895

class GetForecastDataByCoordUseCaseTest {
    @get:Rule
    val mainDispatchRule = MainDispatcherRule()

    private val forecastRepository: ForecastRepository = mockk()
    private val useCase = GetForecastDataByCoordUseCase(forecastRepository = forecastRepository)

    @Test
    fun success() {
        coEvery { forecastRepository.getForecastDataByCoord(coord = any()) } returns mockk()
        runTest {
            val result = useCase(coord = Coord(lat = LAT, lon = LON))

            coVerify(exactly = 1) { forecastRepository.getForecastDataByCoord(coord = Coord(lat = LAT, lon = LON)) }
            assertIs<Result.Success<ForecastData>>(result)
        }
    }

    @Test
    fun failure() {
        coEvery { forecastRepository.getForecastDataByCoord(coord = any()) } throws mockk()
        runTest {
            val result = useCase(coord = Coord(lat = LAT, lon = LON))

            coVerify(exactly = 1) { forecastRepository.getForecastDataByCoord(coord = Coord(lat = LAT, lon = LON)) }
            assertIs<Result.Error>(result)
        }
    }
}
