package jp.co.greensys.weather.app.feature.select.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import jp.co.greensys.weather.app.feature.select.SelectScreen
import kotlinx.serialization.Serializable

@Serializable
object SelectRoute

fun NavController.navigateToSelect() = navigate(route = SelectRoute)

fun NavGraphBuilder.selectScreen(
    navigateUp: () -> Unit,
    navigateToOss: () -> Unit,
) {
    composable<SelectRoute> {
        SelectScreen(
            navigateUp = navigateUp,
            navigateToOss = navigateToOss,
        )
    }
}
