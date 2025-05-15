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
    }
}