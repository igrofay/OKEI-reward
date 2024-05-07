plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "edu.okei.reward"
    compileSdk = 34

    defaultConfig {
        applicationId = "edu.okei.reward"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11"
    }
}

dependencies {
    // Core
    implementation(kotlin("reflect"))
    implementation(project(":core"))
    implementation("androidx.core:core-ktx:1.13.1")
    // Compose
    val composeBom = platform("androidx.compose:compose-bom:2024.04.01")
    implementation(composeBom)
    androidTestImplementation(composeBom)
    implementation("androidx.compose.material:material")
    // Android Studio Preview support
    debugImplementation("androidx.compose.ui:ui-tooling")
    implementation("androidx.compose.ui:ui-tooling-preview")
    // View model compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    // Activity compose
    implementation("androidx.activity:activity-compose:1.9.0")
    // Orbit MVI
    implementation("org.orbit-mvi:orbit-compose:7.1.0")
    // Kodein
    implementation("org.kodein.di:kodein-di-framework-compose:7.20.0")
    // Navigation compose
    implementation("androidx.navigation:navigation-compose:2.7.7")
    // Test
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation(kotlin("reflect"))
}