plugins {
    id("com.android.application")
}

android {
    namespace = "com.th7.hidebootloader"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.th7.hidebootloader"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    compileOnly(files("libs/XposedBridgeApi-82.jar"))
    implementation("com.google.guava:guava:32.1.3-android")
}