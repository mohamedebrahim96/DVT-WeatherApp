package com.dvt.greensys.weather.app.core.screenshot.testing.util

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.test.DarkMode
import androidx.compose.ui.test.DeviceConfigurationOverride
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onRoot
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.github.takahirom.roborazzi.ExperimentalRoborazziApi
import com.github.takahirom.roborazzi.RoborazziOptions
import com.github.takahirom.roborazzi.RoborazziOptions.CompareOptions
import com.github.takahirom.roborazzi.RoborazziOptions.RecordOptions
import com.github.takahirom.roborazzi.captureRoboImage
import com.dvt.greensys.weather.app.core.designsystem.theme.WeatherTheme
import com.dvt.greensys.weather.app.core.ui.LocalTimeZone
import kotlinx.datetime.TimeZone
import org.robolectric.RuntimeEnvironment

private const val ZONE_ID = "Asia/Tokyo"

@OptIn(ExperimentalRoborazziApi::class)
val DefaultRoborazziOptions =
    RoborazziOptions(
        // Pixel-perfect matching
        compareOptions = CompareOptions(changeThreshold = 0f),
        // Reduce the size of the PNGs
        recordOptions = RecordOptions(resizeScale = 0.5),
    )

enum class DefaultTestDevices(val description: String, val spec: String) {
    PHONE("phone", "spec:shape=Normal,width=640,height=360,unit=dp,dpi=480"),
    FOLDABLE("foldable", "spec:shape=Normal,width=673,height=841,unit=dp,dpi=480"),
    TABLET("tablet", "spec:shape=Normal,width=1280,height=800,unit=dp,dpi=480"),
}

fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.captureMultiDevice(
    screenshotName: String,
    body: @Composable () -> Unit,
) {
    DefaultTestDevices.entries.forEach {
        this.captureForDevice(it.description, it.spec, screenshotName, body = body)
    }
}

fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.captureForDevice(
    deviceName: String,
    deviceSpec: String,
    screenshotName: String,
    roborazziOptions: RoborazziOptions = DefaultRoborazziOptions,
    darkMode: Boolean = false,
    body: @Composable () -> Unit,
) {
    val (width, height, dpi) = extractSpecs(deviceSpec)

    // Set qualifiers from specs
    RuntimeEnvironment.setQualifiers("w${width}dp-h${height}dp-${dpi}dpi")

    this.activity.setContent {
        CompositionLocalProvider(
            LocalInspectionMode provides true,
            LocalTimeZone provides TimeZone.of(zoneId = ZONE_ID),
        ) {
            DeviceConfigurationOverride(
                override = DeviceConfigurationOverride.DarkMode(isDarkMode = darkMode),
            ) {
                body()
            }
        }
    }
    this.onRoot()
        .captureRoboImage(
            filePath = "src/test/screenshots/${screenshotName}_$deviceName.png",
            roborazziOptions = roborazziOptions,
        )
}

/**
 * Takes six screenshots combining light/dark and default/Android themes and whether dynamic color
 * is enabled.
 */
fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.captureMultiTheme(
    name: String,
    overrideFileName: String? = null,
    shouldCompareDarkMode: Boolean = true,
    shouldCompareDynamicColor: Boolean = true,
    content: @Composable (desc: String) -> Unit,
) {
    val darkModeValues = if (shouldCompareDarkMode) listOf(true, false) else listOf(false)
    val dynamicThemingValues = if (shouldCompareDynamicColor) listOf(true, false) else listOf(false)

    var darkMode by mutableStateOf(true)
    var dynamicTheming by mutableStateOf(false)

    this.setContent {
        CompositionLocalProvider(
            LocalInspectionMode provides true,
            LocalTimeZone provides TimeZone.of(zoneId = ZONE_ID),
        ) {
            WeatherTheme(
                darkTheme = darkMode,
                disableDynamicTheming = !dynamicTheming,
            ) {
                // Keying is necessary in some cases (e.g. animations)
                key(darkMode, dynamicTheming) {
                    val description = generateDescription(
                        shouldCompareDarkMode,
                        darkMode,
                        shouldCompareDynamicColor,
                        dynamicTheming,
                    )
                    content(description)
                }
            }
        }
    }

    // Create permutations
    darkModeValues.forEach { isDarkMode ->
        darkMode = isDarkMode
        val darkModeDesc = if (isDarkMode) "dark" else "light"

        dynamicThemingValues.forEach dynamicTheme@{ isDynamicTheming ->
            dynamicTheming = isDynamicTheming
            val dynamicThemingDesc = if (isDynamicTheming) "dynamic" else "notDynamic"
            val filename = overrideFileName ?: name

            this.onRoot()
                .captureRoboImage(
                    "src/test/screenshots/" +
                        "$name/$filename" +
                        "_$darkModeDesc" +
                        "_$dynamicThemingDesc" +
                        ".png",
                    roborazziOptions = DefaultRoborazziOptions,
                )
        }
    }
}

fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.captureScreen(
    deviceSpec: String = "spec:shape=Normal,width=450,height=800,unit=dp,dpi=480",
    roborazziOptions: RoborazziOptions = DefaultRoborazziOptions,
    shouldCompareDarkMode: Boolean = true,
    body: @Composable () -> Unit,
) {
    val darkModeValues = if (shouldCompareDarkMode) listOf(true, false) else listOf(false)
    val (width, height, dpi) = extractSpecs(deviceSpec)

    var darkMode by mutableStateOf(true)

    // Set qualifiers from specs
    RuntimeEnvironment.setQualifiers("w${width}dp-h${height}dp-${dpi}dpi")

    darkModeValues.forEach { isDarkMode ->
        darkMode = isDarkMode
        val darkModeDesc = if (isDarkMode) "dark" else "light"

        this.activity.setContent {
            CompositionLocalProvider(
                LocalInspectionMode provides true,
                LocalTimeZone provides TimeZone.of(zoneId = ZONE_ID),
            ) {
                WeatherTheme(darkTheme = darkMode) {
                    key(darkMode) {
                        body()
                    }
                }
            }
        }
        this.onRoot()
            .captureRoboImage(
                filePath = "src/test/screenshots/Screens/${darkModeDesc}_screen.png",
                roborazziOptions = roborazziOptions,
            )
    }
}

@Composable
private fun generateDescription(
    shouldCompareDarkMode: Boolean,
    darkMode: Boolean,
    shouldCompareDynamicColor: Boolean,
    dynamicTheming: Boolean,
): String {
    val description = "" +
        if (shouldCompareDarkMode) {
            if (darkMode) "Dark" else "Light"
        } else {
            ""
        } +
        if (shouldCompareDynamicColor) {
            if (dynamicTheming) " Dynamic" else ""
        } else {
            ""
        }

    return description.trim()
}

/**
 * Extracts some properties from the spec string. Note that this function is not exhaustive.
 */
private fun extractSpecs(deviceSpec: String): TestDeviceSpecs {
    val specs = deviceSpec.substringAfter("spec:")
        .split(",").map { it.split("=") }.associate { it[0] to it[1] }
    val width = specs["width"]?.toInt() ?: 640
    val height = specs["height"]?.toInt() ?: 480
    val dpi = specs["dpi"]?.toInt() ?: 480
    return TestDeviceSpecs(width, height, dpi)
}

data class TestDeviceSpecs(val width: Int, val height: Int, val dpi: Int)
