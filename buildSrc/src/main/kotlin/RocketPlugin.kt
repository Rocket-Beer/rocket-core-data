import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class RocketPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.plugins.run {
            apply("io.gitlab.arturbosch.detekt")
            apply("org.jlleitschuh.gradle.ktlint")
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
    }
}
