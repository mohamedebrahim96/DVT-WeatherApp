package com.co.greensys.weather.app.core.domain

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import com.co.greensys.weather.app.core.common.result.Result
import com.co.greensys.weather.app.core.domain.repository.ForecastRepository
import com.co.greensys.weather.app.core.domain.usecase.GetForecastDataByNameUseCase
import com.co.greensys.weather.app.core.model.ForecastData
import com.co.greensys.weather.app.core.testing.util.MainDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import kotlin.test.Test
import kotlin.test.assertIs

private const val CITY_NAME = "東京都"

class GetForecastDataByNameUseCaseTest {
    @get:Rule
    val mainDispatchRule = MainDispatcherRule()

    private val forecastRepository: ForecastRepository = mockk()
    private val useCase = GetForecastDataByNameUseCase(forecastRepository = forecastRepository)

    @Test
    fun success() {
        coEvery { forecastRepository.getForecastDataByName(name = any()) } returns mockk()
        runTest {
            val result = useCase(cityName = CITY_NAME)

            coVerify(exactly = 1) { forecastRepository.getForecastDataByName(name = CITY_NAME) }
            assertIs<Result.Success<ForecastData>>(result)
        }
    }

    @Test
    fun failure() {
        coEvery { forecastRepository.getForecastDataByName(name = CITY_NAME) } throws mockk()
        runTest {
            val result = useCase(cityName = CITY_NAME)

            coVerify(exactly = 1) { forecastRepository.getForecastDataByName(name = CITY_NAME) }
            assertIs<Result.Error>(result)
        }
    }
}
