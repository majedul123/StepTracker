plugins {
    alias(libs.plugins.majedul.android.feature.ui)
}

android {
    namespace = "com.majedul.auth.presentation"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.auth.domain)
}