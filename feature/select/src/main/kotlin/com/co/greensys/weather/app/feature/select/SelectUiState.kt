package com.dvt.greensys.weather.app.feature.select

import com.dvt.greensys.weather.app.core.model.ForecastData

sealed interface SelectUiState {
    data object Init : SelectUiState

    data object Loading : SelectUiState

    data class Success(
        val forecastData: ForecastData,
    ) : SelectUiState

    data class Error(
        val error: Throwable,
    ) : SelectUiState
}
