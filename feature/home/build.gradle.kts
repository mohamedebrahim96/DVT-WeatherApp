plugins {
    alias(libs.plugins.weather.app.android.feature)
    alias(libs.plugins.weather.app.android.library.compose)
    alias(libs.plugins.weather.app.android.library.jacoco)
    alias(libs.plugins.roborazzi)
}

android {
    namespace = "com.dvt.greensys.weather.app.feature.home"
}

dependencies {
    implementation(projects.core.domain)

    implementation(libs.accompanist.permissions)
    implementation(libs.play.services.location)

    testImplementation(libs.hilt.android.testing)
    testImplementation(libs.robolectric)
    testImplementation(projects.core.screenshotTesting)
    testImplementation(projects.core.testing)
}
