import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.android.build.gradle.BaseExtension
import com.dvt.greensys.weather.app.configureBadgingTasks
import com.dvt.greensys.weather.app.configureKotlinAndroid
import com.dvt.greensys.weather.app.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("com.dropbox.dependency-guard")
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = libs.findVersion("appTargetSdk").get().requiredVersion.toInt()
            }
            extensions.configure<ApplicationAndroidComponentsExtension> {
                configureBadgingTasks(extensions.getByType<BaseExtension>(), this)
            }
        }
    }
}
