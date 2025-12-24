package com.dvt.greensys.weather.app.core.designsystem

import androidx.activity.ComponentActivity
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import dagger.hilt.android.testing.HiltTestApplication
import com.dvt.greensys.weather.app.core.designsystem.component.ActionIconSet
import com.dvt.greensys.weather.app.core.designsystem.component.WeatherTopAppBar
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

@OptIn(ExperimentalMaterial3Api::class)
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(application = HiltTestApplication::class, qualifiers = "480dpi")
@LooperMode(LooperMode.Mode.PAUSED)
class TopBarScreenshotTests {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    @Category(ScreenshotTests::class)
    fun weatherTopAppBar_multipleThemes() {
        composeTestRule.captureMultiTheme(name = "TopAppBar") {
            WeatherTopAppBar(
                titleRes = android.R.string.untitled,
                navigationIcon = ActionIconSet(
                    icon = WeatherIcons.ArrowBack,
                    contentDescription = null,
                ),
                actionIcon = ActionIconSet(
                    icon = WeatherIcons.More,
                    contentDescription = null,
                ),
            )
        }
    }
}
