plugins {
    alias(libs.plugins.majedul.android.library)
    alias(libs.plugins.majedul.jvm.ktor)
}

android {
    namespace = "com.majedul.run.network"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.data)
    implementation(libs.bundles.koin)
}