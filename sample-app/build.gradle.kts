plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdkVersion(apiLevel = 32)
    buildToolsVersion("30.0.3")
    defaultConfig {
        applicationId = "com.rocket.android.core.data.sampe.app"
        minSdkVersion(minSdkVersion = 21)
        targetSdkVersion(targetSdkVersion = 32)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
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
//    implementation(project(":core-data-permissions"))
    implementation("com.rocket.android.core:permissions:0.0.0-alpha01-SNAPSHOT")
    implementation(fileTree("libs") { include("*.jar") })
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.5.20")

    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("androidx.core:core-ktx:1.6.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.fragment:fragment-ktx:1.3.6")
}
