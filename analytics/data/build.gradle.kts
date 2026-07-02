plugins {
    alias(libs.plugins.majedul.android.library)
    alias(libs.plugins.majedul.android.room)
}

android {
    namespace = "com.majedul.analytics.data"
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation(projects.core.database)
    implementation(projects.core.domain)
    implementation(projects.analytics.domain)

    implementation(libs.bundles.koin)
}

