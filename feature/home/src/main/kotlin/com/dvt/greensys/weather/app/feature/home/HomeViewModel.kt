package com.dvt.greensys.weather.app.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvt.greensys.weather.app.core.common.result.Result
import com.dvt.greensys.weather.app.core.domain.usecase.GetForecastDataByCoordUseCase
import com.dvt.greensys.weather.app.core.model.Coord
import com.dvt.greensys.weather.app.core.model.ForecastData
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private val forecastData = MutableStateFlow<Result<ForecastData>?>(null)
    private val locationError = MutableStateFlow<Exception?>(null)

    val homeUiState: StateFlow<HomeUiState> = combine(forecastData, locationError) { data, locError ->
        if (locError != null) {
            return@combine HomeUiState.LocationError(error = locError)
        }

        when (data) {
            null -> HomeUiState.Init
            is Result.Loading -> HomeUiState.Loading
            is Result.Error -> HomeUiState.NetworkError(error = data.error)
            is Result.Success -> {
                // Determine background based on the first forecast item or current weather
                // You might need to adjust 'data.data.list.first().weather[0].main' depending on your model
                val condition = determineWeatherCondition(data.data)

                HomeUiState.Success(
                    forecastData = data.data,
                    currentWeatherCondition = condition
                )
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
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

    private fun determineWeatherCondition(data: ForecastData): WeatherCondition {
        // Logic to parse your domain model. Example:
        // val mainCondition = data.list.firstOrNull()?.weather?.firstOrNull()?.main ?: "Clear"

        // Hardcoded example - Replace with actual logic accessing your ForecastData fields
        val mainCondition = "Clear"

        return when {
            mainCondition.contains("Cloud", true) -> WeatherCondition.CLOUDY
            mainCondition.contains("Rain", true) -> WeatherCondition.RAINY
            else -> WeatherCondition.SUNNY
        }
    }
}