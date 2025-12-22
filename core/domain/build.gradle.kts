plugins {
    alias(libs.plugins.weather.app.android.library)
    alias(libs.plugins.weather.app.android.library.jacoco)
    alias(libs.plugins.ksp)
}

android {
    namespace = "jp.co.greensys.weather.app.core.domain"
}

dependencies {
    api(projects.core.common)
    api(projects.core.model)

    implementation(libs.javax.inject)
    implementation(libs.kotlinx.coroutines.core)

    testImplementation(projects.core.testing)
}
