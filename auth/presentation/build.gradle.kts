plugins {
    alias(libs.plugins.majedul.android.feature.ui)
    alias(libs.plugins.majedul.android.library.compose)
}

android {
    namespace = "com.majedul.presentation"

}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.auth.domain)
}