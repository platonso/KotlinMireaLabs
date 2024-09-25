plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.platon.kotlinmirealabs"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.platon.kotlinmirealabs"
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
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    testImplementation (libs.mockito.core)

    testImplementation ("androidx.test:core:1.6.1")
    testImplementation ("androidx.test.ext:junit:1.2.1")
    testImplementation ("androidx.test:runner:1.6.2")
    testImplementation ("androidx.test:rules:1.6.1")
    testImplementation ("org.mockito:mockito-core:5.2.0")
    testImplementation ("org.mockito:mockito-inline:4.0.0")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.0")
    testImplementation ("org.robolectric:robolectric:4.13")
    testImplementation ("androidx.test:core:1.2.0")
    implementation ("com.squareup.okhttp3:okhttp:4.9.1")


    testImplementation("androidx.test.espresso:espresso-core:3.6.1")
    testImplementation("androidx.test.ext:junit:1.2.1")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")
    testImplementation("org.robolectric:robolectric:4.13")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")
}