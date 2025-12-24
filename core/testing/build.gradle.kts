plugins {
    alias(libs.plugins.weather.app.android.library)
    alias(libs.plugins.weather.app.hilt)
}

android {
    namespace = "com.dvt.greensys.weather.app.core.testing"
}

dependencies {
    api(libs.kotlinx.coroutines.test)
    api(libs.turbine)
    api(projects.core.common)
    api(projects.core.model)

    implementation(libs.androidx.test.rules)
    implementation(libs.hilt.android.testing)
    implementation(libs.kotlinx.datetime)
}
