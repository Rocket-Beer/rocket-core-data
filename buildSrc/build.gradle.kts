plugins {
    `kotlin-dsl`
}

gradlePlugin {
    plugins {
        register("rocket-plugin") {
            id = "rocket-plugin"
            implementationClass = "RocketPlugin"
        }
    }
}

repositories {
    maven("https://plugins.gradle.org/m2/")
    google()
    mavenCentral()
}

dependencies {
    implementation("com.android.tools.build:gradle:4.2.2")
    implementation(kotlin("gradle-plugin", version = "1.5.20"))
    implementation("org.jlleitschuh.gradle:ktlint-gradle:10.1.0")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.17.1")
}
