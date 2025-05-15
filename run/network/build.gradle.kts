plugins {
    alias(libs.plugins.majedul.android.library)
}

android {
    namespace = "com.majedul.run.network"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.data)
}