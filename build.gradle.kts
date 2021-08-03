// Top-level build file where you can add configuration options common to all sub-projects/modules.

allprojects {
    repositories {
        google()
        mavenCentral()

        maven {
            url = java.net.URI("https://maven.pkg.github.com/Rocket-Beer/*")
            credentials {
                username = publish.CommonMethods.getPublisherUserName(project = rootProject)
                password = publish.CommonMethods.getPublisherPassword(project = rootProject)
            }
        }
    }
}

subprojects {
    println("\n********** Configuration for == $project == **********")
    apply(plugin = "rocket-plugin")
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
