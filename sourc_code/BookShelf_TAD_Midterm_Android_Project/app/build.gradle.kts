plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)   // ✅ BẮT BUỘC với Kotlin 2.x
    id("kotlin-kapt")
}

android {
    namespace = "com.tad.bookshelf"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.tad.bookshelf"
        minSdk = 29
        targetSdk = 36
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

//    composeOptions {
//        kotlinCompilerExtensionVersion = "1.5.15"
//    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    // Compose bật, bỏ viewBinding nếu không dùng XML
    buildFeatures {
        compose = true
        viewBinding = false
    }
}

dependencies {
    implementation("com.google.android.material:material:1.13.0")
    implementation(libs.browser)


//    implementation(libs.androidx.core.ktx)
//    implementation(libs.androidx.appcompat)
//    implementation(libs.material)
//    implementation(libs.androidx.activity)
//    implementation(libs.androidx.constraintlayout)
//    implementation(libs.androidx.navigation.fragment.ktx)
//    implementation(libs.androidx.navigation.ui.ktx)
//    testImplementation(libs.junit)
//    androidTestImplementation(libs.androidx.junit)
//    androidTestImplementation(libs.androidx.espresso.core)

    // Compose
    val compose = "1.7.4"
    implementation("androidx.activity:activity-compose:1.9.3")
    implementation("androidx.compose.ui:ui:$compose")
    implementation("androidx.compose.material3:material3:1.3.0")
    implementation("androidx.navigation:navigation-compose:2.8.3")
    debugImplementation("androidx.compose.ui:ui-tooling:$compose")
    implementation("androidx.compose.ui:ui-tooling-preview:$compose")

    // Lifecycle + VM
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.6")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.6")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0")

    // Room
    val room = "2.6.1"
    implementation("androidx.room:room-runtime:$room")
    implementation("androidx.room:room-ktx:$room")
    kapt("androidx.room:room-compiler:$room")

    // Retrofit + Moshi
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.11.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.15.1")

    // Coil (ảnh bìa)
    implementation("io.coil-kt:coil-compose:2.7.0")

    // OkHttp logging (debug)
    debugImplementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
}