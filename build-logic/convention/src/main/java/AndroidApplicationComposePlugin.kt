import com.android.build.api.dsl.ApplicationExtension
import com.majedul.convention.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidApplicationComposePlugin : Plugin<Project> {
    override fun apply(target: Project) {

        target.run {
            pluginManager.run {
                apply("com.android.application")
            }
            pluginManager.apply("majedul.android.application.compose")
            configureAndroidCompose(commonExtension = extensions.getByType<ApplicationExtension>())
        }
    }
}