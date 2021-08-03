package publish

import org.gradle.api.Project
import java.io.File
import java.util.Properties

object CommonMethods {

    private const val PUBLISH_PROPERTIES = "publish.properties"
    private const val PUBLISH_REPO_NAME = "repo.name"
    private const val PUBLISH_REPO_URL = "repo.url"
    private const val PUBLISH_GROUP_ID = "groupId"
    private const val PUBLISH_ARTIFACT_ID = "artifactId"
    private const val PUBLISH_ARTIFACT = "artifact"
    private const val PUBLISH_VERSION = "version"

    private const val LOCAL_PROPERTIES = "local.properties"
    private const val LOCAL_PUBLISHER_USERNAME = "github.username"
    private const val LOCAL_PUBLISHER_TOKEN = "github.token"
    private const val GITHUB_ACTIONS_PUBLISHER_USERNAME = "GITHUB_ACTOR"
    private const val GITHUB_ACTIONS_PUBLISHER_TOKEN = "GITHUB_TOKEN"

    fun getPublishRepoName(projectDir: Project): String {
        return getPublishProperty(projectDir, PUBLISH_REPO_NAME)
    }

    private fun getPublishProperty(project: Project, property: String): String {
        val value = getPublisherProperties(project.projectDir.path).getProperty(property) ?: ""
        println("$project $PUBLISH_PROPERTIES -> $property = $value")
        return value
    }

    private fun getPublisherProperties(projectDir: String): Properties {
        val properties = Properties()
        val propertiesFile = File("$projectDir/$PUBLISH_PROPERTIES")
        if (propertiesFile.exists()) {
            properties.load(propertiesFile.inputStream())
        }
        return properties
    }

    fun getPublishRepoUrl(projectDir: Project): String {
        return getPublishProperty(projectDir, PUBLISH_REPO_URL)
    }

    fun getPublishGroupId(projectDir: Project): String {
        return getPublishProperty(projectDir, PUBLISH_GROUP_ID)
    }

    fun getPublishArtifactId(projectDir: Project): String {
        return getPublishProperty(projectDir, PUBLISH_ARTIFACT_ID)
    }

    fun getPublishArtifact(projectDir: Project): String {
        return "${projectDir.buildDir}${getPublishProperty(projectDir, PUBLISH_ARTIFACT)}"
    }

    fun getPublishVersion(projectDir: Project): String {
        return getPublishProperty(projectDir, PUBLISH_VERSION)
    }

    fun getPublisherUserName(project: Project): String {
        var name = getLocalProperty(project = project, property = LOCAL_PUBLISHER_USERNAME)
        if (name.isEmpty()) name = System.getenv(GITHUB_ACTIONS_PUBLISHER_USERNAME)
        return name
    }

    private fun getLocalProperty(project: Project, property: String): String {
        return getLocalProperties(projectDir = project.projectDir.path).getProperty(property) ?: ""
    }

    private fun getLocalProperties(projectDir: String): Properties {
        val properties = Properties()
        val propertiesFile = File("$projectDir/$LOCAL_PROPERTIES")
        if (propertiesFile.exists()) {
            properties.load(propertiesFile.inputStream())
        }
        return properties
    }

    fun getPublisherPassword(project: Project): String {
        var password = getLocalProperty(project = project, property = LOCAL_PUBLISHER_TOKEN)
        if (password.isEmpty()) password = System.getenv(GITHUB_ACTIONS_PUBLISHER_TOKEN)
        return password
    }
}

