package jp.co.greensys.weather.app.feature.home

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.PermissionState
import dagger.hilt.android.testing.HiltTestApplication
import jp.co.greensys.weather.app.core.screenshot.testing.category.ScreenshotTests
import jp.co.greensys.weather.app.core.screenshot.testing.util.captureScreen
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
class HomeScreenScreenshotTests {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    @OptIn(ExperimentalPermissionsApi::class)
    @Category(ScreenshotTests::class)
    fun homeScreen() {
        composeTestRule.captureScreen {
            HomeScreen(
                uiState = HomeUiState.Init,
                locationPermissionsState = DummyLocationPermissionsState,
                onOssClick = {},
                onSelectClick = {},
                onLocationClick = {},
                onBottomSheetDismissRequest = {},
                onErrorDialogOkClick = {},
            )
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
private val DummyLocationPermissionsState = object : MultiplePermissionsState {
    override val allPermissionsGranted: Boolean = true
    override val permissions: List<PermissionState> = emptyList()
    override val revokedPermissions: List<PermissionState> = emptyList()
    override val shouldShowRationale: Boolean = false
    override fun launchMultiplePermissionRequest() = Unit
}
