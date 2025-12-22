package com.dvt.weatherapp.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import jp.co.greensys.weather.app.navigation.WeatherNavHost

@Composable
fun WeatherApp(
    appState: WeatherAppState = rememberWeatherAppState(),
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        contentWindowInsets = WindowInsets(left = 0, top = 0, right = 0, bottom = 0),
    ) { innerPadding ->
        WeatherNavHost(
            appState = appState,
            modifier = Modifier
                .consumeWindowInsets(paddingValues = innerPadding)
                .windowInsetsPadding(insets = WindowInsets.safeDrawing.only(sides = WindowInsetsSides.Horizontal)),
        )
    }
}
