plugins {
    alias(libs.plugins.weather.app.android.library)
    alias(libs.plugins.weather.app.android.library.jacoco)
    alias(libs.plugins.weather.app.hilt)
}

android {
    namespace = "com.dvt.greensys.weather.app.core.data"
}

dependencies {
    api(projects.core.common)

    implementation(projects.core.domain)
    implementation(projects.core.network)

    testImplementation(projects.core.testing)
}
