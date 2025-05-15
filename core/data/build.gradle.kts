plugins {
    alias(libs.plugins.majedul.android.library)
}

android {
    namespace = "com.majedul.core.data"
}

dependencies {

    implementation(libs.timber)
    implementation(projects.core.domain)
    implementation(projects.core.database)

}