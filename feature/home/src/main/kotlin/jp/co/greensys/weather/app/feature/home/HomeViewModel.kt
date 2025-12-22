package jp.co.greensys.weather.app.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.greensys.weather.app.core.common.result.Result
import jp.co.greensys.weather.app.core.domain.usecase.GetForecastDataByCoordUseCase
import jp.co.greensys.weather.app.core.model.Coord
import jp.co.greensys.weather.app.core.model.ForecastData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getForecastDataByCoordUseCase: GetForecastDataByCoordUseCase,
) : ViewModel() {
    private val forecastData = MutableStateFlow<Result<ForecastData>?>(value = null)
    private val locationError = MutableStateFlow<Exception?>(value = null)

    val homeUiState: StateFlow<HomeUiState> = combine(forecastData, locationError) { forecastData, locationError ->
        if (locationError != null) {
            return@combine HomeUiState.LocationError(error = locationError)
        }

        when (forecastData) {
            null -> HomeUiState.Init
            is Result.Loading -> HomeUiState.Loading
            is Result.Success -> HomeUiState.Success(forecastData = forecastData.data)
            is Result.Error -> HomeUiState.NetworkError(error = forecastData.error)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
        initialValue = HomeUiState.Init,
    )

    fun getForecastData(coord: Coord) {
        viewModelScope.launch {
            forecastData.value = Result.Loading
            forecastData.value = getForecastDataByCoordUseCase(coord = coord)
        }
    }

    fun clearForecastData() {
        viewModelScope.launch {
            forecastData.value = null
            locationError.value = null
        }
    }

    fun getLocationError(error: Exception) {
        Timber.e(error)
        locationError.value = error
    }
}
