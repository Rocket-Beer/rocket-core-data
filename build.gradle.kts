// Top-level build file where you can add configuration options common to all sub-projects/modules.

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "rocket-plugin")
    println("\n********** Configuration for == $project == **********")
    apply(from = "$rootDir/config/publish/publish.gradle")
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
