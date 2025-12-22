package jp.co.greensys.weather.app.core.ui

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import dagger.hilt.android.testing.HiltTestApplication
import jp.co.greensys.weather.app.core.model.Forecast
import jp.co.greensys.weather.app.core.model.ForecastData
import jp.co.greensys.weather.app.core.screenshot.testing.category.ScreenshotTests
import jp.co.greensys.weather.app.core.screenshot.testing.util.captureMultiTheme
import jp.co.greensys.weather.app.core.ui.component.DetailContent
import kotlinx.datetime.Instant
import org.junit.Rule
import org.junit.experimental.categories.Category
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import org.robolectric.annotation.LooperMode
import kotlin.test.Test

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(application = HiltTestApplication::class, qualifiers = "480dpi")
@LooperMode(LooperMode.Mode.PAUSED)
class DetailContentScreenshots {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    @Category(ScreenshotTests::class)
    fun detailContent_multipleThemes() {
        composeTestRule.captureMultiTheme(name = "DetailContent") {
            DetailContent(
                forecastData = ForecastData(
                    cityName = "東京都",
                    date = Instant.parse("2024-09-09T03:00:00Z"),
                    forecastList = listOf(
                        Forecast(
                            date = Instant.parse("2024-09-09T03:00:00Z"),
                            maxTemp = 31.82f,
                            minTemp = 30.27f,
                            humidity = 67,
                            iconId = "02d",
                            pop = 0.38f,
                        ),
                        Forecast(
                            date = Instant.parse("2024-09-09T06:00:00Z"),
                            maxTemp = 32.26f,
                            minTemp = 31.34f,
                            humidity = 58,
                            iconId = "02d",
                            pop = 0.39f,
                        ),
                        Forecast(
                            date = Instant.parse("2024-09-09T09:00:00Z"),
                            maxTemp = 30.44f,
                            minTemp = 30.44f,
                            humidity = 58,
                            iconId = "04n",
                            pop = 0.31f,
                        ),
                        Forecast(
                            date = Instant.parse("2024-09-09T12:00:00Z"),
                            maxTemp = 28.35f,
                            minTemp = 28.35f,
                            humidity = 69,
                            iconId = "10n",
                            pop = 0.55f,
                        ),
                        Forecast(
                            date = Instant.parse("2024-09-09T15:00:00Z"),
                            maxTemp = 27.31f,
                            minTemp = 27.31f,
                            humidity = 73,
                            iconId = "10n",
                            pop = 0.88f,
                        ),
                        Forecast(
                            date = Instant.parse("2024-09-09T18:00:00Z"),
                            maxTemp = 26.92f,
                            minTemp = 26.92f,
                            humidity = 75,
                            iconId = "10n",
                            pop = 0.91f,
                        ),
                        Forecast(
                            date = Instant.parse("2024-09-09T21:00:00Z"),
                            maxTemp = 26.3f,
                            minTemp = 26.3f,
                            humidity = 80,
                            iconId = "10d",
                            pop = 0.21f,
                        ),
                        Forecast(
                            date = Instant.parse("2024-09-10T00:00:00Z"),
                            maxTemp = 28.47f,
                            minTemp = 28.47f,
                            humidity = 69,
                            iconId = "02d",
                            pop = 0.06f,
                        ),
                    ),
                ),
            )
        }
    }
}
