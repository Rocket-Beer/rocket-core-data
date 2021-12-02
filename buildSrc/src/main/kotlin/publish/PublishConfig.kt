package publish

import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.register

fun Project.configPublish() {
    plugins.apply("maven-publish")

    (extensions.getByName("publishing") as? PublishingExtension)?.run {
        repositories {
            maven {
                name = CommonMethods.getPublishRepoName(projectDir = this@configPublish)
                setUrl(CommonMethods.getPublishRepoUrl(projectDir = this@configPublish))
                credentials {
                    username =
                        CommonMethods.getPublisherUserName(project = rootProject)
                    password =
                        CommonMethods.getPublisherPassword(project = rootProject)
                }
            }
        }

        publications.register("gpr", MavenPublication::class) {
            groupId = CommonMethods.getPublishGroupId(projectDir = this@configPublish)
            artifactId = CommonMethods.getPublishArtifactId(projectDir = this@configPublish)
            version = CommonMethods.getPublishVersion(projectDir = this@configPublish)
            artifact(CommonMethods.getPublishArtifact(projectDir = this@configPublish))

            pom.withXml {
                val dependenciesNode = asNode().appendNode("dependencies")

                configurations.named("implementation").get().allDependencies.forEach {
                    val dependencyNode = dependenciesNode.appendNode("dependency")
                    dependencyNode.appendNode("groupId", it.group)
                    dependencyNode.appendNode("artifactId", it.name)
                    dependencyNode.appendNode("version", it.version)
                    // Add scope?
                }
            }
        }
    }
}
