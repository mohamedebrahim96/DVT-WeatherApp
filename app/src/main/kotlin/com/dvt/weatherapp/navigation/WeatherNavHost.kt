package com.dvt.weatherapp.navigation
/**
 * Created by @mohamedebrahim96
 * Email: ebrahimm131@gmail.com
 * Website: https://mohamedebrahim96.com/
 * <p>
 * Created on 22/12/2025
 */


import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import jp.co.greensys.weather.app.feature.home.navigation.HomeRoute
import jp.co.greensys.weather.app.feature.home.navigation.homeScreen
import jp.co.greensys.weather.app.feature.select.navigation.navigateToSelect
import jp.co.greensys.weather.app.feature.select.navigation.selectScreen
import jp.co.greensys.weather.app.ui.WeatherAppState
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