package publish

import org.gradle.api.Project
import java.io.File
import java.util.Properties

object CommonMethods {

    private const val LOCAL_PROPERTIES = "local.properties"
    private const val LOCAL_PUBLISHER_USERNAME = "github.username"
    private const val LOCAL_PUBLISHER_TOKEN = "github.token"
    private const val GITHUB_ACTIONS_PUBLISHER_USERNAME = "GITHUB_ACTOR"
    private const val GITHUB_ACTIONS_PUBLISHER_TOKEN = "GITHUB_TOKEN"

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
