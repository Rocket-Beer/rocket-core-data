import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.register

class RocketPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.plugins.run {
            apply("io.gitlab.arturbosch.detekt")
            apply("org.jlleitschuh.gradle.ktlint")
            apply("maven-publish")
        }

        (project.extensions.getByName("detekt") as? DetektExtension)?.run {
            toolVersion = "1.18.0-RC1"
            input = project.files(
                "src/main/java", "src/test/java", "src/main/kotlin", "src/test/kotlin"
            )
            parallel = false
            config = project.files("${project.rootDir}/config/lint/detekt/detekt-config.yml")
            buildUponDefaultConfig = false
            allRules = false
            disableDefaultRuleSets = false
            debug = false
            ignoreFailures = false
            ignoredBuildTypes = listOf("release")
            ignoredFlavors = listOf("production")
            ignoredVariants = listOf("productionRelease")
            basePath = project.path

            reports {
                xml {
                    enabled = true
                    destination = project.file("build/reports/detekt.xml")
                }

                html {
                    enabled = true
                    destination = project.file("build/reports/detekt.html")
                }

                txt {
                    enabled = true
                    destination = project.file("build/reports/detekt.txt")
                }

                sarif {
                    enabled = true
                    destination = project.file("build/reports/detekt.sarif")
                }

                custom {
                    // The simple class name of your custom report.
                    reportId = "CustomJsonReport"
                    destination = project.file("build/reports/detekt.json")
                }
            }
        }

        (project.extensions.getByName("publishing") as? PublishingExtension)?.run {
            repositories {
                maven {
                    name = publish.CommonMethods.getPublishRepoName(projectDir = project)
                    setUrl(publish.CommonMethods.getPublishRepoUrl(projectDir = project))
                    credentials {
                        username =
                            publish.CommonMethods.getPublisherUserName(project = project.rootProject)
                        password =
                            publish.CommonMethods.getPublisherPassword(project = project.rootProject)
                    }
                }
            }

            publications.register("gpr", MavenPublication::class) {
                groupId = publish.CommonMethods.getPublishGroupId(projectDir = project)
                artifactId = publish.CommonMethods.getPublishArtifactId(projectDir = project)
                version = publish.CommonMethods.getPublishVersion(projectDir = project)
                artifact(publish.CommonMethods.getPublishArtifact(projectDir = project))
            }
        }
    }
}
