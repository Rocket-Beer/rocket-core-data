plugins {
    id("com.android.library")
    kotlin("android")
    id("maven-publish")
}

android {
    compileSdkVersion(apiLevel = 30)

    defaultConfig {
        minSdkVersion(minSdkVersion = 21)
        targetSdkVersion(targetSdkVersion = 30)

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile(name = "proguard-android-optimize.txt"),
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
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.5.20")

    implementation("com.karumi:dexter:6.2.3")

    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito:mockito-inline:3.11.2")
    testImplementation("org.mockito:mockito-core:3.11.2")
}
