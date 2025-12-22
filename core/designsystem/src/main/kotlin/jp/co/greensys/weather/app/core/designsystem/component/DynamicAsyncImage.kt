package jp.co.greensys.weather.app.core.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter.State.Error
import coil3.compose.rememberAsyncImagePainter
import jp.co.greensys.weather.app.core.designsystem.theme.WeatherTheme

@Composable
fun DynamicAsyncImage(
    imageUrl: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    placeholder: Painter = ColorPainter(color = Color.Transparent),
) {
    val isError = remember { mutableStateOf(value = false) }
    val imageLoader = rememberAsyncImagePainter(
        model = imageUrl,
        onState = { state ->
            isError.value = state is Error
        },
    )

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Image(
            contentScale = ContentScale.Crop,
            painter = if (isError.value.not()) imageLoader else placeholder,
            contentDescription = contentDescription,
            modifier = Modifier.matchParentSize(),
        )
    }
}

@PreviewLightDark
@Composable
private fun DynamicAsyncImagePreview() {
    WeatherTheme {
        DynamicAsyncImage(
            imageUrl = "",
            contentDescription = null,
            modifier = Modifier.size(size = 150.dp),
        )
    }
}
