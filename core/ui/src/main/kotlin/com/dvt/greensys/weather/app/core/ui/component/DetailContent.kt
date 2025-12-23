package com.co.greensys.weather.app.core.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.co.greensys.weather.app.core.common.util.toString
import com.co.greensys.weather.app.core.designsystem.component.DynamicAsyncImage
import com.co.greensys.weather.app.core.designsystem.theme.Blue
import com.co.greensys.weather.app.core.designsystem.theme.Red
import com.co.greensys.weather.app.core.designsystem.theme.WeatherTheme
import com.co.greensys.weather.app.core.designsystem.theme.dimens
import com.co.greensys.weather.app.core.model.Forecast
import com.co.greensys.weather.app.core.model.ForecastData
import com.co.greensys.weather.app.core.ui.LocalTimeZone
import com.co.greensys.weather.app.core.ui.R
import com.co.greensys.weather.app.core.ui.mock.PreviewForecastData

@Composable
fun DetailContent(
    forecastData: ForecastData,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets(left = 0.dp, top = 0.dp, right = 0.dp, bottom = 0.dp),
    ) { innerPadding ->
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = innerPadding,
            modifier = Modifier.fillMaxSize(),
        ) {
            item {
                Text(
                    text = forecastData.cityName,
                    style = MaterialTheme.typography.headlineMedium,
                )
            }
            item {
                Text(
                    text = forecastData.date.toString(
                        format = stringResource(id = R.string.ui_full_date_format),
                        timeZone = LocalTimeZone.current,
                    ),
                )
            }
            item {
                PopChart(
                    list = forecastData.forecastList,
                    label = stringResource(id = R.string.ui_pop),
                    modifier = Modifier.padding(
                        top = MaterialTheme.dimens.medium,
                        bottom = MaterialTheme.dimens.small,
                    ),
                )
            }
            items(
                items = forecastData.forecastList,
                key = { it.date.toString() },
                itemContent = { forecast -> DetailContentItem(forecast = forecast) },
            )
        }
    }
}

@Composable
fun DetailContentItem(
    forecast: Forecast,
    modifier: Modifier = Modifier,
) {
    val timeZone = LocalTimeZone.current

    ListItem(
        headlineContent = {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    val date = forecast.date.toString(
                        format = stringResource(id = R.string.ui_time_format),
                        timeZone = timeZone,
                    )
                    if (date.startsWith(prefix = "00")) {
                        Text(
                            text = forecast.date.toString(
                                format = stringResource(id = R.string.ui_tomorrow_date_format),
                                timeZone = timeZone,
                            ),
                        )
                    }
                    Text(text = date)
                }
                DynamicAsyncImage(
                    imageUrl = createIconUrl(iconId = forecast.iconId),
                    contentDescription = null,
                    modifier = Modifier.size(size = 110.dp),
                )
                Column {
                    Text(
                        text = stringResource(id = R.string.ui_max_temp, forecast.maxTemp),
                        color = Red,
                    )
                    Text(
                        text = stringResource(id = R.string.ui_min_temp, forecast.minTemp),
                        color = Blue,
                    )
                    Text(text = stringResource(id = R.string.ui_humidity, forecast.humidity))
                }
            }
        },
        modifier = modifier,
    )
}

private fun createIconUrl(iconId: String): String =
    "https://openweathermap.org/img/wn/$iconId@4x.png"

@PreviewLightDark
@Composable
private fun DetailContentPreview() {
    WeatherTheme {
        DetailContent(
            forecastData = PreviewForecastData.default,
        )
    }
}
