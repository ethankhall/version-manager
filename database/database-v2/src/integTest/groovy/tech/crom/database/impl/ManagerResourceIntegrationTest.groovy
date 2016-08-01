package tech.crom.database.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification
import tech.crom.config.DatabaseConfig
import tech.crom.database.api.CommitManager
import tech.crom.database.api.ProjectManager
import tech.crom.database.api.RepoManager

@ContextConfiguration(classes = [DatabaseConfig.class], loader = SpringApplicationContextLoader.class)
class ManagerResourceIntegrationTest extends Specification {

    @Autowired
    CommitManager commitManager

    @Autowired
    ProjectManager projectManager

    @Autowired
    RepoManager repoManager

    def 'test crud'() {
        when:
        def project = projectManager.createProject('newProject')

        then:
        projectManager.findProject(project.projectName) == project
        projectManager.findProject(project.projectUid) == project
    }
}
