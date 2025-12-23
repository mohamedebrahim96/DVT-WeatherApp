package com.dvt.greensys.weather.app.core.designsystem

import androidx.activity.ComponentActivity
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import dagger.hilt.android.testing.HiltTestApplication
import com.dvt.greensys.weather.app.core.designsystem.component.WeatherButton
import com.dvt.greensys.weather.app.core.designsystem.component.WeatherTextButton
import com.dvt.greensys.weather.app.core.designsystem.icon.WeatherIcons
import com.dvt.greensys.weather.app.core.screenshot.testing.category.ScreenshotTests
import com.dvt.greensys.weather.app.core.screenshot.testing.util.captureMultiTheme
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
class ButtonScreenshotTests {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    @Category(ScreenshotTests::class)
    fun weatherButton_multipleThemes() {
        composeTestRule.captureMultiTheme(name = "Button") { description ->
            Surface {
                WeatherButton(onClick = {}, text = { Text(text = "$description Button") })
            }
        }
    }

    @Test
    @Category(ScreenshotTests::class)
    fun weatherButton_leadingIcon_multipleThemes() {
        composeTestRule.captureMultiTheme(
            name = "Button",
            overrideFileName = "ButtonLeadingIcon",
        ) { description ->
            Surface {
                WeatherButton(
                    onClick = {},
                    text = { Text(text = "$description Icon Button") },
                    leadingIcon = { Icon(imageVector = WeatherIcons.Gps, contentDescription = null) },
                )
            }
        }
    }

    @Test
    @Category(ScreenshotTests::class)
    fun weatherTextButton_leadingIcon_multipleThemes() {
        composeTestRule.captureMultiTheme(
            name = "Button",
            overrideFileName = "TextButton",
        ) { description ->
            Surface {
                WeatherTextButton(
                    onClick = {},
                    content = { Text(text = "$description Text Button") },
                )
            }
        }
    }
}
