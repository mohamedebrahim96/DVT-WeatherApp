package jp.co.greensys.weather.app.feature.home

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import jp.co.greensys.weather.app.core.common.result.Result
import jp.co.greensys.weather.app.core.domain.usecase.GetForecastDataByCoordUseCase
import jp.co.greensys.weather.app.core.model.Coord
import jp.co.greensys.weather.app.core.testing.util.MainDispatcherRule
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertIs

class HomeViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getForecastDataByCoordUseCase: GetForecastDataByCoordUseCase = mockk()
    private lateinit var viewModel: HomeViewModel

    @BeforeTest
    fun setup() {
        viewModel = HomeViewModel(getForecastDataByCoordUseCase = getForecastDataByCoordUseCase)
    }

    @Test
    fun init_state() {
        runTest {
            val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.homeUiState.collect() }

            assertIs<HomeUiState.Init>(viewModel.homeUiState.value)

            collectJob.cancel()
        }
    }

    @Test
    fun getForecastData_success() {
        coEvery { getForecastDataByCoordUseCase(coord = any()) } returns Result.Success(data = mockk())

        runTest {
            val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.homeUiState.collect() }

            with(viewModel) {
                getForecastData(coord = Coord(lat = 35.6895, lon = 139.6917))
                coVerify(exactly = 1) { getForecastDataByCoordUseCase(coord = Coord(lat = 35.6895, lon = 139.6917)) }

                assertIs<HomeUiState.Success>(homeUiState.value)
            }

            collectJob.cancel()
        }
    }

    @Test
    fun getForecastData_error() {
        coEvery { getForecastDataByCoordUseCase(coord = any()) } returns Result.Error(error = mockk())

        runTest {
            val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.homeUiState.collect() }

            with(viewModel) {
                getForecastData(coord = Coord(lat = 35.6895, lon = 139.6917))
                coVerify(exactly = 1) { getForecastDataByCoordUseCase(coord = Coord(lat = 35.6895, lon = 139.6917)) }

                assertIs<HomeUiState.NetworkError>(homeUiState.value)

                clearForecastData()
                assertIs<HomeUiState.Init>(homeUiState.value)
            }

            collectJob.cancel()
        }
    }

    @Test
    fun getLocationError() {
        runTest {
            val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.homeUiState.collect() }

            with(viewModel) {
                getLocationError(error = mockk())

                assertIs<HomeUiState.LocationError>(homeUiState.value)

                clearForecastData()
                assertIs<HomeUiState.Init>(homeUiState.value)
            }

            collectJob.cancel()
        }
    }
}
