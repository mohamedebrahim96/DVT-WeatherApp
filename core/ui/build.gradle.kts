plugins {
    alias(libs.plugins.weather.app.android.library)
    alias(libs.plugins.weather.app.android.library.compose)
    alias(libs.plugins.roborazzi)
}

android {
    namespace = "com.dvt.greensys.weather.app.core.ui"
}

dependencies {
    api(projects.core.designsystem)
    api(projects.core.model)

    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.mpandroidchart)
    implementation(projects.core.common)

    testImplementation(libs.androidx.compose.ui.test)
    testImplementation(libs.hilt.android.testing)
    testImplementation(libs.robolectric)
    testImplementation(projects.core.screenshotTesting)
    testImplementation(projects.core.testing)
}
