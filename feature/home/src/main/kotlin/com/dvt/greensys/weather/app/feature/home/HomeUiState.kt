package com.dvt.greensys.weather.app.feature.home

import com.dvt.greensys.weather.app.core.model.ForecastData

sealed interface HomeUiState {
    data object Init : HomeUiState

    data object Loading : HomeUiState

    data class Success(
        val forecastData: ForecastData,
    ) : HomeUiState

    data class NetworkError(
        val error: Throwable,
    ) : HomeUiState

    data class LocationError(
        val error: Throwable,
    ) : HomeUiState
}
