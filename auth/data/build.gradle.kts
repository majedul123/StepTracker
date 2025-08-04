plugins {
    alias(libs.plugins.majedul.android.library)
    alias(libs.plugins.majedul.jvm.ktor)
}

android {
    namespace = "com.majedul.auth.data"
}

dependencies {
    implementation(libs.bundles.koin)

    implementation(projects.auth.domain)
    implementation(projects.core.domain)
    implementation(projects.core.data)
}