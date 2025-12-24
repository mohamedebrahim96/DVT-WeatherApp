plugins {
    alias(libs.plugins.weather.app.android.library)
    alias(libs.plugins.weather.app.android.library.compose)
    alias(libs.plugins.roborazzi)
}

android {
    namespace = "com.dvt.greensys.weather.app.core.designsystem"
}

dependencies {
    api(libs.androidx.compose.material.iconsExtended)
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.runtime)

    implementation(libs.coil.kt.compose)
    implementation(libs.coil.network.okhttp)
    implementation(projects.core.model)

    testImplementation(libs.hilt.android.testing)
    testImplementation(libs.robolectric)
    testImplementation(projects.core.screenshotTesting)
}
