package com.dvt.greensys.weather.app.feature.select

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import dagger.hilt.android.testing.HiltTestApplication
import com.dvt.greensys.weather.app.core.screenshot.testing.category.ScreenshotTests
import com.dvt.greensys.weather.app.core.screenshot.testing.util.captureScreen
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
class SelectScreenScreenshotTests {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    @Category(ScreenshotTests::class)
    fun selectScreen() {
        composeTestRule.captureScreen {
            SelectScreen(
                uiState = SelectUiState.Init,
                onOssClick = {},
                onBackClick = {},
                onCityClick = {},
                onBottomSheetDismissRequest = {},
                onErrorDialogOkClick = {},
            )
        }
    }
}
