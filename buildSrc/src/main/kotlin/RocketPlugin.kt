
import detekt.configDetekt
import org.gradle.api.Plugin
import org.gradle.api.Project
import publish.configPublish

class RocketPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.configDetekt()
        project.configPublish()
    }
}
