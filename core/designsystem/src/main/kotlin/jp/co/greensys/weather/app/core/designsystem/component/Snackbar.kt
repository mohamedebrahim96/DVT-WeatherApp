package jp.co.greensys.weather.app.core.designsystem.component

import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import jp.co.greensys.weather.app.core.designsystem.theme.Orange
import jp.co.greensys.weather.app.core.designsystem.theme.WeatherTheme

@Composable
fun WeatherSnackbar(
    snackbarData: SnackbarData,
    modifier: Modifier = Modifier,
) {
    Snackbar(
        snackbarData = snackbarData,
        containerColor = Orange,
        contentColor = Color.White,
        actionColor = Color.White,
        modifier = modifier,
    )
}

@PreviewLightDark
@Composable
private fun WeatherSnackbarPreview() {
    WeatherTheme {
        WeatherSnackbar(snackbarData = DummySnackbarData)
    }
}

private val DummySnackbarData = object : SnackbarData {
    override val visuals: SnackbarVisuals = object : SnackbarVisuals {
        override val actionLabel: String
            get() = "action"
        override val duration: SnackbarDuration = SnackbarDuration.Short
        override val message: String
            get() = LoremIpsum(words = 5).values.first()
        override val withDismissAction: Boolean
            get() = false
    }

    override fun dismiss() {}
    override fun performAction() {}
}
