plugins {
    alias(libs.plugins.majedul.android.library)
    alias(libs.plugins.majedul.jvm.ktor)

}

android {
    namespace = "com.majedul.core.data"
}

dependencies {

    implementation(libs.timber)
    implementation(projects.core.domain)
    implementation(projects.core.database)
    implementation(libs.bundles.koin)


}