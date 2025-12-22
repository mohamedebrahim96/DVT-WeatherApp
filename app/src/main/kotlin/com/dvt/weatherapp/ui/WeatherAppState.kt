package com.dvt.weatherapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun rememberWeatherAppState(
    navController: NavHostController = rememberNavController(),
): WeatherAppState = remember(
    navController,
) {
    WeatherAppState(
        navController = navController,
    )
}

@Stable
class WeatherAppState(
    val navController: NavHostController,
)
