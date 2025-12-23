package com.dvt.greensys.weather.app.feature.select

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dvt.greensys.weather.app.core.designsystem.component.ActionIconSet
import com.dvt.greensys.weather.app.core.designsystem.component.MenuSet
import com.dvt.greensys.weather.app.core.designsystem.component.WeatherLoading
import com.dvt.greensys.weather.app.core.designsystem.component.WeatherTopAppBar
import com.dvt.greensys.weather.app.core.designsystem.icon.WeatherIcons
import com.dvt.greensys.weather.app.core.designsystem.theme.WeatherTheme
import com.dvt.greensys.weather.app.core.ui.component.DetailContent
import com.dvt.greensys.weather.app.core.ui.component.ErrorDialog
import com.dvt.greensys.weather.app.feature.select.component.CityItem
import com.dvt.greensys.weather.app.feature.select.preview.SelectPreviewParameterProvider
import com.dvt.greensys.weather.app.core.ui.R as UiR

@Composable
internal fun SelectScreen(
    navigateUp: () -> Unit,
    navigateToOss: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SelectViewModel = hiltViewModel(),
) {
    val uiState by viewModel.selectUiState.collectAsStateWithLifecycle()

    SelectScreen(
        uiState = uiState,
        onOssClick = navigateToOss,
        onBackClick = navigateUp,
        onCityClick = viewModel::getForecastData,
        onBottomSheetDismissRequest = viewModel::clearForecastData,
        onErrorDialogOkClick = viewModel::clearForecastData,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectScreen(
    uiState: SelectUiState,
    onOssClick: () -> Unit,
    onBackClick: () -> Unit,
    onCityClick: (city: String) -> Unit,
    onBottomSheetDismissRequest: () -> Unit,
    onErrorDialogOkClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    // status バーを考慮したボトムシート用の padding
    val bottomSheetPaddingValues = WindowInsets.safeDrawing.only(sides = WindowInsetsSides.Top).asPaddingValues()

    Scaffold(
        topBar = {
            WeatherTopAppBar(
                titleRes = R.string.select_title,
                navigationIcon = ActionIconSet(
                    icon = WeatherIcons.ArrowBack,
                    contentDescription = stringResource(id = UiR.string.ui_content_description_back),
                    onClick = onBackClick,
                ),
                actionIcon = ActionIconSet(
                    icon = WeatherIcons.More,
                    contentDescription = stringResource(id = UiR.string.ui_content_description_menu),
                    menu = listOf(
                        MenuSet(textRes = UiR.string.ui_oss, onClick = onOssClick),
                    ),
                ),
            )
        },
        modifier = modifier,
    ) { innerPadding ->
        val cities = stringArrayResource(id = R.array.select_cities)

        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier.fillMaxSize(),
        ) {
            items(items = cities, key = { it }) {
                CityItem(cityName = it, onClick = { onCityClick(it) })
            }
        }
    }

    when (uiState) {
        is SelectUiState.Init -> Unit // no-op
        is SelectUiState.Loading -> WeatherLoading(
            hasScrim = true,
            modifier = Modifier.fillMaxSize(),
        )

        is SelectUiState.Error -> ErrorDialog(
            onDismissRequest = onErrorDialogOkClick,
            title = stringResource(id = UiR.string.ui_error_loading_title),
            text = stringResource(id = UiR.string.ui_error_loading_message),
        )

        is SelectUiState.Success -> ModalBottomSheet(
            onDismissRequest = onBottomSheetDismissRequest,
            containerColor = MaterialTheme.colorScheme.background,
            modifier = Modifier.padding(paddingValues = bottomSheetPaddingValues),
        ) {
            DetailContent(
                forecastData = uiState.forecastData,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun SelectScreenPreview(
    @PreviewParameter(SelectPreviewParameterProvider::class)
    uiState: SelectUiState,
) {
    WeatherTheme {
        SelectScreen(
            uiState = uiState,
            onOssClick = {},
            onBackClick = {},
            onCityClick = {},
            onBottomSheetDismissRequest = {},
            onErrorDialogOkClick = {},
        )
    }
}
