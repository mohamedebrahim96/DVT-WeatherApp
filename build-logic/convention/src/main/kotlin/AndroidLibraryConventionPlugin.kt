import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.android.build.gradle.LibraryExtension
import com.dvt.weatherapp.weather.app.configureKotlinAndroid
import com.dvt.weatherapp.weather.app.disableUnnecessaryAndroidTests
import com.dvt.weatherapp.weather.app.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = libs.findVersion("appTargetSdk").get().requiredVersion.toInt()
                // The resource prefix is derived from the module name,
                // so resources inside ":feature:module1" must be prefixed with "module1_"
                resourcePrefix = path.split("""\W""".toRegex()).drop(1).distinct()
                    .filter { it != "core" && it != "feature" }
                    .joinToString(separator = "_").lowercase() + "_"
            }
            extensions.configure<LibraryAndroidComponentsExtension> {
                disableUnnecessaryAndroidTests(target)
            }

            dependencies {
                add("implementation", libs.findLibrary("timber").get())
                add("testImplementation", kotlin("test"))
                add("testImplementation", libs.findLibrary("mockk").get())
            }
        }
    }
}
