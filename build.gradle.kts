// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) version "2.1.21" apply false
    alias(libs.plugins.google.gms.google.services) apply false
    alias(libs.plugins.compose.compiler) apply false
    id("com.google.devtools.ksp") version "2.1.21-2.0.1"
    id("com.google.dagger.hilt.android") version "2.55" apply false

}