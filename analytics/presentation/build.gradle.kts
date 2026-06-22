plugins {
    alias(libs.plugins.majedul.android.feature.ui)
}

android {
    namespace = "com.majedul.analytics.presentation"

}

dependencies {
    implementation(projects.analytics.domain)
}