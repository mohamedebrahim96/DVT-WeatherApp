package com.dvt.greensys.weather.app.feature.home.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.dvt.greensys.weather.app.feature.home.HomeUiState

internal class HomePreviewParameterProvider : PreviewParameterProvider<HomeUiState> {
    override val values: Sequence<HomeUiState> = sequenceOf(
        HomeUiState.Init,
        HomeUiState.Loading,
        HomeUiState.NetworkError(error = Exception()),
        HomeUiState.LocationError(error = Exception()),
    )
}
