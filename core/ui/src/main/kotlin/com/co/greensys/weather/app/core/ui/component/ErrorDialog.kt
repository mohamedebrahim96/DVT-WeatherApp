package com.dvt.greensys.weather.app.core.ui.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import com.dvt.greensys.weather.app.core.designsystem.component.WeatherTextButton
import com.dvt.greensys.weather.app.core.designsystem.theme.WeatherTheme
import com.dvt.greensys.weather.app.core.ui.R

@Composable
fun ErrorDialog(
    onDismissRequest: () -> Unit,
    title: String,
    modifier: Modifier = Modifier,
    text: String? = null,
) = AlertDialog(
    onDismissRequest = onDismissRequest,
    confirmButton = {
        WeatherTextButton(
            onClick = onDismissRequest,
            content = { Text(text = stringResource(id = R.string.ui_ok)) },
        )
    },
    modifier = modifier,
    title = { Text(text = title) },
    text = text?.let {
        { Text(text = text) }
    },
)

@PreviewLightDark
@Composable
private fun ErrorDialogPreview() {
    WeatherTheme {
        ErrorDialog(
            onDismissRequest = {},
            title = "Error",
            text = LoremIpsum(words = 30).values.first(),
        )
    }
}
