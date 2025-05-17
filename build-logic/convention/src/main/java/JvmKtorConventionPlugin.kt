import com.majedul.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class JvmKtorConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            //TODO
        //  pluginManager.apply("org.jetbrains.kotlin:kotlin-serialization:1.9.0")

            dependencies {
                "implementation"(libs.findBundle("ktor").get())
            }
        }
    }
}