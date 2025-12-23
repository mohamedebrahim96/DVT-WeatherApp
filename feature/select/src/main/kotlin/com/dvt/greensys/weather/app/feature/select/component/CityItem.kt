package com.dvt.greensys.weather.app.feature.select.component

import androidx.compose.foundation.clickable
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.dvt.greensys.weather.app.core.designsystem.theme.WeatherTheme

@Composable
internal fun CityItem(
    cityName: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: ListItemColors = ListItemDefaults.colors(),
    enabled: Boolean = true,
) {
    ListItem(
        headlineContent = { Text(text = cityName) },
        colors = colors,
        modifier = modifier.clickable(
            enabled = enabled,
            onClick = onClick,
        ),
    )
}

@PreviewLightDark
@Composable
private fun CityItemPreview() {
    WeatherTheme {
        CityItem(cityName = "東京都", onClick = {})
    }
}
