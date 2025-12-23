package com.dvt.greensys.weather.app.core.designsystem

import androidx.activity.ComponentActivity
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import dagger.hilt.android.testing.HiltTestApplication
import com.dvt.greensys.weather.app.core.designsystem.component.WeatherSnackbar
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
class SnackbarScreenshotTests {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    @Category(ScreenshotTests::class)
    fun weatherSnackbar_multipleThemes() {
        composeTestRule.captureMultiTheme(name = "Snackbar") {
            WeatherSnackbar(snackbarData = DummySnackbarData)
        }
    }
}

private val DummySnackbarData = object : SnackbarData {
    override val visuals: SnackbarVisuals = object : SnackbarVisuals {
        override val actionLabel: String
            get() = "action"
        override val duration: SnackbarDuration = SnackbarDuration.Short
        override val message: String
            get() = LoremIpsum(words = 5).values.first()
        override val withDismissAction: Boolean
            get() = false
    }

    override fun dismiss() {}
    override fun performAction() {}
}
