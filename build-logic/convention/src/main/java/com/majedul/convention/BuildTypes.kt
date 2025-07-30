package com.majedul.convention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.BuildType
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

internal fun Project.configureBuildTypes(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    extensionsType: ExtensionsType
) {
    val apiKey = gradleLocalProperties(rootDir, providers).getProperty("API_KEY")
    when (extensionsType) {
        ExtensionsType.APPLICATION -> {
            commonExtension.run {

                buildFeatures{
                    buildConfig = true
                }
                extensions.configure<ApplicationExtension> {
                    buildTypes {
                        debug {
                            configureDebugBuildType(apiKey)
                        }
                        release {
                          configureReleaseBuildType(apiKey,commonExtension)
                        }
                    }
                }
            }
        }

        ExtensionsType.LIBRARY -> {
            commonExtension.run {
                buildFeatures{
                    buildConfig = true
                }
                extensions.configure<LibraryExtension> {
                    buildTypes {
                        debug {
                            configureDebugBuildType(apiKey)
                        }
                        release {
                            configureReleaseBuildType(apiKey,commonExtension)
                        }
                    }
                }
            }
        }
    }
}

private fun BuildType.configureDebugBuildType(apiKey: String) {
    buildConfigField("String", "api_key", "\"$apiKey\"")
    buildConfigField("String", "base_url", "\"http://localhost:8086\"")
}

private fun BuildType.configureReleaseBuildType(
    apiKey: String, commonExtension: CommonExtension<*, *, *, *, *, *>
) {
    buildConfigField("String", "api_key", "\"$apiKey\"")
    buildConfigField("String", "base_url", "\"http://localhost:8086\"")
    isMinifyEnabled = true
    proguardFiles(
        commonExtension.getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
    )
}