plugins {
    alias(libs.plugins.majedul.android.library)
   // alias(libs.plugins.majedul.android.room)
}

android {
    namespace = "com.majedul.core.database"
}

dependencies {
    implementation(libs.org.mongodb.bson)
    implementation(projects.core.domain)
    implementation(libs.androidx.junit.ktx)
}