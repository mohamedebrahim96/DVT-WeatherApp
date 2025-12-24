package com.dvt.greensys.weather.app.core.ui.component

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.dvt.greensys.weather.app.core.designsystem.component.DynamicAsyncImage
import com.dvt.greensys.weather.app.core.designsystem.theme.Blue
import com.dvt.greensys.weather.app.core.designsystem.theme.Red
import com.dvt.greensys.weather.app.core.designsystem.theme.WeatherTheme
import com.dvt.greensys.weather.app.core.designsystem.theme.dimens
import com.dvt.greensys.weather.app.core.model.ForecastData
import com.dvt.greensys.weather.app.core.model.ForecastItem
import com.dvt.greensys.weather.app.core.ui.R
import com.dvt.greensys.weather.app.core.ui.mock.PreviewForecastData
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
                    // FIX: Access city name correctly
                    text = forecastData.city.name,
                    style = MaterialTheme.typography.headlineMedium,
                )
            }
            item {
                // FIX: Get date from the first item in the list
                val firstItemDate = forecastData.list.firstOrNull()?.dt ?: System.currentTimeMillis()
                val date = Date(firstItemDate * 1000)
                val format = SimpleDateFormat("EEEE, d MMMM", Locale.getDefault())

                Text(
                    text = format.format(date),
                )
            }
            item {
                PopChart(
                    // FIX: Access .list instead of .forecastList
                    list = forecastData.list,
                    label = stringResource(id = R.string.ui_pop),
                    modifier = Modifier.padding(
                        top = MaterialTheme.dimens.medium,
                        bottom = MaterialTheme.dimens.small,
                    ),
                )
            }
            items(
                // FIX: Access .list
                items = forecastData.list,
                key = { it.dt }, // Use unique timestamp as key
                itemContent = { forecast -> DetailContentItem(forecast = forecast) },
            )
        }
    }
}

@Composable
fun DetailContentItem(
    forecast: ForecastItem, // FIX: Use ForecastItem instead of Forecast
    modifier: Modifier = Modifier,
) {
    // Helper to format date strings
    val timeFormat = remember { SimpleDateFormat("HH:mm", Locale.getDefault()) }
    val dateObj = Date(forecast.dt * 1000)

    ListItem(
        headlineContent = {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = timeFormat.format(dateObj))
                }

                DynamicAsyncImage(
                    // FIX: Access icon from the weather list safely
                    imageUrl = createIconUrl(iconId = forecast.weather.firstOrNull()?.icon ?: "01d"),
                    contentDescription = null,
                    modifier = Modifier.size(size = 50.dp), // Reduced size slightly
                )

                Column {
                    // FIX: Access temp properties from .main
                    Text(
                        text = stringResource(id = R.string.ui_max_temp, forecast.main.tempMax),
                        color = Red,
                    )
                    Text(
                        text = stringResource(id = R.string.ui_min_temp, forecast.main.tempMin),
                        color = Blue,
                    )
                    Text(text = stringResource(id = R.string.ui_humidity, forecast.main.humidity))
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