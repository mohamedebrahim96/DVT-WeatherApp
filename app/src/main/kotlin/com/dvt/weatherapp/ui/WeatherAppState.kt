package com.dvt.weatherapp.ui
/**
 * Created by @mohamedebrahim96
 * Email: ebrahimm131@gmail.com
 * Website: https://mohamedebrahim96.com/
 * <p>
 * Created on 22/12/2025
 */
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
