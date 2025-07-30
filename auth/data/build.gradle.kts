plugins {
    alias(libs.plugins.majedul.android.library)
    alias(libs.plugins.majedul.jvm.ktor)

}

android {
    namespace = "com.majedul.data"
}

dependencies {
    implementation(libs.bundles.koin)
    implementation(projects.core.domain)
    implementation(projects.core.data)
    implementation(projects.auth.domain)
}