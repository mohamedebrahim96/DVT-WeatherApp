package com.dvt.greensys.weather.app.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.dvt.greensys.weather.app.core.designsystem.theme.Orange
import com.dvt.greensys.weather.app.core.designsystem.theme.WeatherTheme
import com.dvt.greensys.weather.app.core.designsystem.theme.dimens
import com.dvt.greensys.weather.app.core.model.ForecastItem
import com.dvt.greensys.weather.app.core.ui.mock.PreviewForecastData
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PopChart(
    list: List<ForecastItem>,
    modifier: Modifier = Modifier,
    label: String? = null,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceBright,
) {
    // FIX: Use SimpleDateFormat explicitly instead of the missing extension function
    val dateFormat = remember { SimpleDateFormat("H:mm", Locale.getDefault()) }

    val labels = list.map {
        val date = Date(it.dt * 1000)
        dateFormat.format(date)
    }

    val entries = list.mapIndexed { index, forecast ->
        Entry(index.toFloat(), (forecast.pop * 100).toFloat())
    }

    val dataSet = LineDataSet(entries, "pop").apply {
        valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String = "${value.toInt()}%"
        }
        color = Orange.toArgb()
        circleColors = listOf(Orange.toArgb())
        lineWidth = 2f
        circleRadius = 4f
    }

    Column(
        modifier = modifier
            .background(color = backgroundColor)
            .padding(vertical = MaterialTheme.dimens.small),
    ) {
        label?.let {
            Text(
                text = label,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
        }
        AndroidView(
            factory = { context ->
                LineChart(context).apply {
                    description.isEnabled = false
                    legend.isEnabled = false
                    setScaleEnabled(false)
                    setTouchEnabled(false)

                    axisLeft.apply {
                        axisMaximum = 100f
                        axisMinimum = 0f
                        labelCount = 5
                        setDrawAxisLine(false)
                        setDrawLabels(false)
                    }

                    axisRight.isEnabled = false

                    xAxis.apply {
                        position = XAxis.XAxisPosition.BOTTOM
                        setDrawAxisLine(false)
                        setDrawGridLines(false)
                        valueFormatter = IndexAxisValueFormatter(labels)
                    }

                    data = LineData(dataSet)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .sizeIn(minHeight = 150.dp)
                .padding(horizontal = MaterialTheme.dimens.medium),
        )
    }
}

@PreviewLightDark
@Composable
private fun PopChartPreview() {
    WeatherTheme {
        Surface {
            PopChart(
                list = PreviewForecastData.default.list,
                label = "Precipitation",
            )
        }
    }
}