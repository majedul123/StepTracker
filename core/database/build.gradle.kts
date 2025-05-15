plugins {
    alias(libs.plugins.majedul.android.library)
}

android {
    namespace = "com.majedul.core.database"
}

dependencies {
    implementation(libs.org.mongodb.bson)
    implementation(projects.core.domain)
    implementation(libs.androidx.junit.ktx)
}