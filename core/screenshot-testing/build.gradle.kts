plugins {
    alias(libs.plugins.weather.app.android.library)
    alias(libs.plugins.weather.app.android.library.compose)
    alias(libs.plugins.weather.app.hilt)
}

android {
    namespace = "com.dvt.greensys.weather.app.core.screenshottesting"
}

dependencies {
    api(libs.bundles.androidx.compose.ui.test)
    api(libs.roborazzi)
    implementation(libs.androidx.activity.compose)
    implementation(libs.robolectric)
    implementation(projects.core.common)
    implementation(projects.core.designsystem)
    implementation(projects.core.ui)
}
