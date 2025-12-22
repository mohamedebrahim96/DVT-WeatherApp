package jp.co.greensys.weather.app.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.PreviewLightDark
import jp.co.greensys.weather.app.core.designsystem.theme.Orange
import jp.co.greensys.weather.app.core.designsystem.theme.WeatherTheme

@Composable
fun WeatherLoading(
    modifier: Modifier = Modifier,
    hasScrim: Boolean = false,
) {
    val backgroundColor = if (hasScrim) {
        MaterialTheme.colorScheme.scrim.copy(alpha = 0.6f)
    } else {
        Color.Transparent
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(color = backgroundColor)
            .clickable(enabled = false, onClick = {})
            .then(other = modifier),
    ) {
        CircularProgressIndicator(color = Orange, strokeCap = StrokeCap.Round)
    }
}

@PreviewLightDark
@Composable
private fun MicrowaveLoadingPreview() {
    WeatherTheme {
        WeatherLoading(modifier = Modifier.fillMaxSize())
    }
}
