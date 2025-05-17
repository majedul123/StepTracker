plugins {
    alias(libs.plugins.majedul.android.feature.ui)
}

android {
    namespace = "com.majedul.presentation"
    compileSdk = 34
}

dependencies {
     implementation(projects.core.domain)
}