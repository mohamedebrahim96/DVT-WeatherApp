package jp.co.greensys.weather.app.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import jp.co.greensys.weather.app.core.designsystem.icon.WeatherIcons
import jp.co.greensys.weather.app.core.designsystem.theme.Orange
import jp.co.greensys.weather.app.core.designsystem.theme.WeatherTheme

@Composable
fun WeatherButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = Orange,
            contentColor = Color.White,
        ),
        contentPadding = contentPadding,
        content = content,
    )
}

@Composable
fun WeatherButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: @Composable () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    WeatherButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        contentPadding = if (leadingIcon != null) {
            ButtonDefaults.ButtonWithIconContentPadding
        } else {
            ButtonDefaults.ContentPadding
        },
    ) {
        WeatherButtonContent(
            text = text,
            leadingIcon = leadingIcon,
        )
    }
}

@Composable
fun WeatherTextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit,
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.textButtonColors(contentColor = Orange),
        content = content,
    )
}

@Composable
private fun WeatherButtonContent(
    text: @Composable () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    if (leadingIcon != null) {
        Box(
            modifier = Modifier.sizeIn(maxHeight = ButtonDefaults.IconSize),
            content = { leadingIcon() },
        )
    }
    Box(
        modifier = Modifier
            .padding(
                start = if (leadingIcon != null) {
                    ButtonDefaults.IconSpacing
                } else {
                    0.dp
                },
            ),
        content = { text() },
    )
}

@PreviewLightDark
@Composable
private fun WeatherButtonPreview() {
    WeatherTheme {
        val text = LoremIpsum(words = 2).values.first()

        Column(modifier = Modifier.background(MaterialTheme.colorScheme.surface)) {
            WeatherButton(onClick = {}) { Text(text = text) }
            WeatherButton(onClick = {}, enabled = false) { Text(text = text) }
        }
    }
}

@PreviewLightDark
@Composable
private fun WeatherButtonLeadingIconPreview() {
    WeatherTheme {
        val text = LoremIpsum(words = 2).values.first()

        Column(modifier = Modifier.background(MaterialTheme.colorScheme.surface)) {
            WeatherButton(
                onClick = {},
                text = { Text(text = text) },
                leadingIcon = { Icon(imageVector = WeatherIcons.Gps, contentDescription = null) },
            )
            WeatherButton(
                onClick = {},
                enabled = false,
                text = { Text(text = text) },
                leadingIcon = { Icon(imageVector = WeatherIcons.Gps, contentDescription = null) },
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun WeatherTextButtonPreview() {
    WeatherTheme {
        val text = LoremIpsum(words = 2).values.first()

        Column(modifier = Modifier.background(MaterialTheme.colorScheme.surface)) {
            WeatherTextButton(
                onClick = {},
                content = { Text(text = text) },
            )
            WeatherTextButton(
                onClick = {},
                enabled = false,
                content = { Text(text = text) },
            )
        }
    }
}
