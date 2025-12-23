package com.dvt.greensys.weather.app.navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.dvt.greensys.weather.app.feature.home.navigation.HomeRoute
import com.dvt.greensys.weather.app.feature.home.navigation.homeScreen
import com.dvt.greensys.weather.app.feature.select.navigation.navigateToSelect
import com.dvt.greensys.weather.app.feature.select.navigation.selectScreen
import com.dvt.greensys.weather.app.ui.WeatherAppState

@Composable
internal fun WeatherNavHost(
    appState: WeatherAppState,
    modifier: Modifier = Modifier,
) {
    val navController = appState.navController
    val navigateToOss = {
        navController.context.startActivity(Intent(navController.context, OssLicensesMenuActivity::class.java))
    }

    NavHost(
        navController = navController,
        startDestination = HomeRoute,
        modifier = modifier,
    ) {
        homeScreen(
            navigateToSelect = navController::navigateToSelect,
            navigateToOss = navigateToOss,
        )
        selectScreen(
            navigateUp = navController::navigateUp,
            navigateToOss = navigateToOss,
        )
    }
}
