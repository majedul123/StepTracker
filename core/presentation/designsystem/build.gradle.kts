plugins {
    alias(libs.plugins.majedul.android.library.compose)
}

android {
    namespace = "com.majedul.core.presentation.designsystem"
}

dependencies {

    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
}