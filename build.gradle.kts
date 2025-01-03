@file:Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed

buildscript {
    dependencies {
        classpath("com.android.tools.build:gradle")
        classpath("com.google.gms:google-services")
        classpath("com.google.firebase:firebase-crashlytics-gradle")
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.jetbrains.kotlin.serialization) apply false
    alias(libs.plugins.google.dagger.hilt.android) apply false
    alias(libs.plugins.mikepenz.aboutlibraries) apply false
    alias(libs.plugins.google.gms.services) apply false
    alias(libs.plugins.google.firebase.crashlytics) apply false
    alias(libs.plugins.google.devtools.ksp) apply false
    alias(libs.plugins.compose.compiler) apply false
}
