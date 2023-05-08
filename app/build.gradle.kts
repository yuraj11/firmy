import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    alias(libs.plugins.google.dagger.hilt.android)
    alias(libs.plugins.mikepenz.aboutlibraries)
    alias(libs.plugins.google.gms.services)
    alias(libs.plugins.google.firebase.crashlytics)
    kotlin("kapt")
}

android {
    namespace = "sk.devprog.firmy"
    compileSdk = 33

    defaultConfig {
        applicationId = "sk.devprog.firmy"
        minSdk = 26
        targetSdk = 33
        versionCode = 2
        versionName = "0.1.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    signingConfigs {
        create("release") {
            val localProperties = gradleLocalProperties(rootDir)
            storeFile = rootProject.file(localProperties["storeFilePath"] as String)
            storePassword = localProperties["storePassword"] as String
            keyPassword = localProperties["keyPassword"] as String
            keyAlias = localProperties["keyAlias"] as String
        }
    }
    buildTypes {
        getByName("debug") {
            val localProperties = gradleLocalProperties(rootDir)
            manifestPlaceholders["mapApiKeyValue"] = localProperties.getProperty("mapsDebugKey").orEmpty()
        }
        getByName("release") {
            val localProperties = gradleLocalProperties(rootDir)
            manifestPlaceholders["mapApiKeyValue"] = localProperties.getProperty("mapsReleaseKey").orEmpty()
            isDebuggable = false
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    kotlin {
        jvmToolchain(17)
    }
}

dependencies {
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    implementation(libs.google.dagger.hilt.android)
    kapt(libs.google.dagger.hilt.android.compiler)

    implementation(libs.google.maps.android.compose)
    implementation(libs.google.maps.play.services)
    implementation(platform(libs.google.firebase.bom))
    implementation(libs.google.firebase.crashlytics.ktx)

    implementation(libs.io.ktor.client.core)
    implementation(libs.io.ktor.client.android)
    implementation(libs.io.ktor.client.content.negotiation)
    implementation(libs.io.ktor.client.logging)
    implementation(libs.io.ktor.serialization.json)

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    annotationProcessor(libs.androidx.room.compiler)
    kapt(libs.androidx.room.compiler)

    implementation(libs.mikepenz.aboutlibraries.compose)
    implementation(libs.jakewharton.timber)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.compose.ui.test.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))

    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}