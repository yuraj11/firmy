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
    alias(libs.plugins.google.devtools.ksp)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "sk.devprog.firmy"
    compileSdk = 35

    defaultConfig {
        applicationId = "sk.devprog.firmy"
        minSdk = 26
        targetSdk = 35
        versionCode = 4
        versionName = "1.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    signingConfigs {
        create("release") {
            val localProperties = gradleLocalProperties(rootDir, providers)
            storeFile = localProperties["storeFilePath"]?.let {
                rootProject.file(it as String)
            }
            storePassword = localProperties["storePassword"] as? String
            keyPassword = localProperties["keyPassword"] as? String
            keyAlias = localProperties["keyAlias"] as? String
        }
    }
    buildTypes {
        getByName("debug") {
            val localProperties = gradleLocalProperties(rootDir, providers)
            manifestPlaceholders["mapApiKeyValue"] = localProperties.getProperty("mapsDebugKey").orEmpty()
        }
        getByName("release") {
            val localProperties = gradleLocalProperties(rootDir, providers)
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
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
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
    ksp(libs.google.dagger.hilt.android.compiler)

    implementation(libs.google.maps.android.compose)
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
    ksp(libs.androidx.room.compiler)

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