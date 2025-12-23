package com.dvt.greensys.weather.app.feature.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dvt.greensys.weather.app.feature.home.HomeScreen
import kotlinx.serialization.Serializable

@Serializable
object HomeRoute

fun NavGraphBuilder.homeScreen(
    navigateToSelect: () -> Unit,
    navigateToOss: () -> Unit,
) {
    composable<HomeRoute> {
        HomeScreen(
            navigateToSelect = navigateToSelect,
            navigateToOss = navigateToOss,
        )
    }
}
