import com.dvt.greensys.weather.app.BuildType

plugins {
    alias(libs.plugins.weather.app.android.application)
    alias(libs.plugins.weather.app.android.application.compose)
    alias(libs.plugins.weather.app.android.application.jacoco)
    alias(libs.plugins.weather.app.hilt)
    alias(libs.plugins.google.oss.licenses)
}

android {
    namespace = "com.dvt.greensys.weather.app"

    defaultConfig {
        applicationId = "com.dvt.greensys.weather.app"
        versionCode = libs.versions.appVersionCode.get().toInt()
        versionName = libs.versions.appVersionName.get()
    }

    buildTypes {
        debug {
            applicationIdSuffix = BuildType.DEBUG.applicationIdSuffix
        }
        release {
            isMinifyEnabled = true
            // fake signingConfig
            signingConfig = signingConfigs.named("debug").get()
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"))
        }
    }

    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }

    buildFeatures { buildConfig = true }
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.data)
    implementation(projects.core.designsystem)
    implementation(projects.core.domain)
    implementation(projects.core.ui)
    implementation(projects.feature.home)
    implementation(projects.feature.select)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.profileinstaller)
    implementation(libs.google.oss.licenses)
    implementation(libs.timber)

    debugImplementation(libs.leakcanary)

    kspTest(libs.hilt.compiler)
}

dependencyGuard {
    configuration("releaseRuntimeClasspath")
}
