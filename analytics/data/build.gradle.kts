plugins {
    alias(libs.plugins.majedul.android.library)
}

android {
    namespace = "com.majedul.analytics.data"
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation(projects.core.database)
    implementation(projects.core.domain)
    implementation(projects.analytics.domain)

}

