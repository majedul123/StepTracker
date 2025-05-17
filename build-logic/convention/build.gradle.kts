plugins {
    `kotlin-dsl`
}

group = "com.majedul.buildlogic"

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
}

// register plugin
gradlePlugin{
    plugins{
        register("androidApplication"){
            id = "majedul.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"

        }
        register("androidApplicationCompose"){
            id = "majedul.android.application.compose"
            implementationClass = "AndroidApplicationComposePlugin"

        }
        register("androidLibrary"){
            id = "majedul.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidLibraryCompose"){
            id = "majedul.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }

        register("androidFeatureUI"){
            id = "majedul.android.feature.ui"
            implementationClass = "AndroidFeatureUiConventionPlugin"
        }
        register("androidRoom"){
            id = "majedul.android.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }
        register("jvmLibrary"){
            id = "majedul.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }
    }
}