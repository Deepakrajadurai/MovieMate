plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.projects.moviemates"
    compileSdk = 36
    // CORRECTED: Use a stable SDK version

    defaultConfig {
        applicationId = "com.projects.moviemates"
        minSdk = 26 // CORRECTED: Use a realistic minimum SDK
        targetSdk = 34 // CORRECTED: Use a stable target SDK
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
    buildFeatures {
        dataBinding = true
        // viewBinding true // You can have both ViewBinding and DataBinding enabled
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation("com.google.code.gson:gson:2.10.1")

    implementation("com.squareup.picasso:picasso:2.8")

    // Location Services for GPS
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation("com.google.android.gms:play-services-tasks:18.1.0")

    // Authentication dependencies
    implementation(platform("com.google.firebase:firebase-bom:32.0.0"))
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.android.gms:play-services-auth:20.5.0")

    // Retrofit for network calls
    val retrofitVersion = "2.9.0"
    implementation("com.squareup.retrofit2:retrofit:${retrofitVersion}")
    implementation("com.squareup.retrofit2:converter-gson:${retrofitVersion}")

    // OkHttp (Recommended to include explicitly with Retrofit)
    val okhttpVersion = "4.12.0"
    implementation("com.squareup.okhttp3:okhttp:${okhttpVersion}")
    implementation("com.squareup.okhttp3:logging-interceptor:${okhttpVersion}")

    // Media3 (ExoPlayer)
    val media3Version = "1.2.0"
    implementation("androidx.media3:media3-exoplayer:${media3Version}")
    implementation("androidx.media3:media3-ui:${media3Version}")
    implementation("androidx.media3:media3-exoplayer-dash:${media3Version}")
    implementation("androidx.media3:media3-exoplayer-hls:${media3Version}")

//    annotationProcessor("androidx.media3:media3-ui:${media3Version}")

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}