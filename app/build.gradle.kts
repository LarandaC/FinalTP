plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("plugin.serialization") version "2.0.21"
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.wallpics"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.wallpics"
        minSdk = 25
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.room.common)
    val nav_version = "2.8.4"

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation ("androidx.compose.material:material-icons-extended:1.7.6")
    //implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")

    // Jetpack Compose Integration
    implementation("androidx.navigation:navigation-compose:$nav_version")

    // JSON serialization library, works with the Kotlin serialization plugin.
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")

    implementation ("io.coil-kt:coil-compose:2.2.2") // Imagenes
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0") // Para JSON

    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation("androidx.compose.ui:ui:1.5.0") // Versión de Compose
    implementation("androidx.activity:activity-ktx:1.6.0")
    implementation("androidx.recyclerview:recyclerview:1.3.0")
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.0")

    // Fonts
    implementation ("androidx.compose.ui:ui-text-google-fonts:1.3.0")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //BD Local
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    ksp("androidx.room:room-compiler:$room_version")
}