plugins {
    alias(libs.plugins.weather.app.android.feature)
    alias(libs.plugins.weather.app.android.library.compose)
    alias(libs.plugins.weather.app.android.library.jacoco)
    alias(libs.plugins.roborazzi)
}

android {
    namespace = "jp.co.greensys.weather.app.feature.select"
}

dependencies {
    implementation(projects.core.domain)

    testImplementation(libs.hilt.android.testing)
    testImplementation(libs.robolectric)
    testImplementation(projects.core.screenshotTesting)
    testImplementation(projects.core.testing)
}
