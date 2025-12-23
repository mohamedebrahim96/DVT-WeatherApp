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
import com.dvt.greensys.weather.app.core.common.util.toString
import com.dvt.greensys.weather.app.core.designsystem.theme.Orange
import com.dvt.greensys.weather.app.core.designsystem.theme.WeatherTheme
import com.dvt.greensys.weather.app.core.designsystem.theme.dimens
import com.dvt.greensys.weather.app.core.model.Forecast
import com.dvt.greensys.weather.app.core.ui.LocalTimeZone
import com.dvt.greensys.weather.app.core.ui.mock.PreviewForecastData

@Composable
fun PopChart(
    list: List<Forecast>,
    modifier: Modifier = Modifier,
    label: String? = null,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceBright,
) {
    val labels = list.map { it.date.toString(format = "H:mm", timeZone = LocalTimeZone.current) }
    val entries = list.mapIndexed { index, forecast ->
        Entry(index.toFloat(), forecast.popPercent.toFloat())
    }
    val dataSet = LineDataSet(entries, "pop").apply {
        // 値を整数表記に上書き
        valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String = "${value.toInt()}%"
        }
        color = Orange.toArgb()
        circleColors = listOf(Orange.toArgb())
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
                    // 説明文を非表示
                    description.isEnabled = false
                    // 凡例を非表示
                    legend.isEnabled = false
                    // スケールを不変にセット
                    setScaleEnabled(false)
                    // タッチ無効
                    setTouchEnabled(false)

                    // 左のY軸
                    axisLeft.apply {
                        // 最大値
                        axisMaximum = 100f
                        // 最小値
                        axisMinimum = 0f
                        // ガイドラインの本数
                        labelCount = 5
                        // 軸線の非表示
                        setDrawAxisLine(false)
                        // ラベルの非表示
                        setDrawLabels(false)
                    }

                    // 右のY軸
                    axisRight.isEnabled = false

                    // X軸
                    xAxis.apply {
                        // 軸線を下に
                        position = XAxis.XAxisPosition.BOTTOM
                        // 軸線を非表示
                        setDrawAxisLine(false)
                        // ガイドライン非表示
                        setDrawGridLines(false)
                        // ラベルの設定
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
                list = PreviewForecastData.default.forecastList,
                label = "降水確率",
            )
        }
    }
}
