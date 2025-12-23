package com.co.greensys.weather.app.feature.select.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.co.greensys.weather.app.core.ui.mock.PreviewForecastData
import com.co.greensys.weather.app.feature.select.SelectUiState

internal class SelectPreviewParameterProvider : PreviewParameterProvider<SelectUiState> {
    override val values: Sequence<SelectUiState> = sequenceOf(
        SelectUiState.Init,
        SelectUiState.Loading,
        SelectUiState.Success(forecastData = PreviewForecastData.default),
        SelectUiState.Error(error = Exception()),
    )
}
