package com.dvt.greensys.weather.app.feature.select

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import com.dvt.greensys.weather.app.core.common.result.Result
import com.dvt.greensys.weather.app.core.domain.usecase.GetForecastDataByNameUseCase
import com.dvt.greensys.weather.app.core.testing.util.MainDispatcherRule
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertIs

private const val CITY_NAME = "東京都"

class SelectViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getForecastDataByNameUseCase: GetForecastDataByNameUseCase = mockk()
    private lateinit var viewModel: SelectViewModel

    @BeforeTest
    fun setup() {
        viewModel = SelectViewModel(getForecastDataByNameUseCase = getForecastDataByNameUseCase)
    }

    @Test
    fun init_state() {
        runTest {
            val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.selectUiState.collect() }

            assertIs<SelectUiState.Init>(viewModel.selectUiState.value)

            collectJob.cancel()
        }
    }

    @Test
    fun getForecastData_success() {
        coEvery { getForecastDataByNameUseCase(cityName = any()) } returns Result.Success(data = mockk())

        runTest {
            val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.selectUiState.collect() }

            with(viewModel) {
                getForecastData(cityName = CITY_NAME)
                coVerify(exactly = 1) { getForecastDataByNameUseCase(cityName = CITY_NAME) }

                assertIs<SelectUiState.Success>(selectUiState.value)
            }

            collectJob.cancel()
        }
    }

    @Test
    fun getForecastData_error() {
        coEvery { getForecastDataByNameUseCase(cityName = any()) } returns Result.Error(error = mockk())

        runTest {
            val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.selectUiState.collect() }

            with(viewModel) {
                getForecastData(cityName = CITY_NAME)
                coVerify(exactly = 1) { getForecastDataByNameUseCase(cityName = CITY_NAME) }

                assertIs<SelectUiState.Error>(selectUiState.value)

                clearForecastData()
                assertIs<SelectUiState.Init>(selectUiState.value)
            }

            collectJob.cancel()
        }
    }
}
