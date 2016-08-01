package tech.crom.business.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tech.crom.business.api.ProjectApi
import tech.crom.database.api.ProjectManager
import tech.crom.model.project.CromProject
import tech.crom.security.authorization.api.PermissionService
import java.util.*

@Service
class DefaultProjectApi @Autowired constructor(
    val projectManager: ProjectManager,
    val permissionService: PermissionService
): ProjectApi {

    override fun projectExists(projectName: String): Boolean {
        return projectManager.findProject(projectName) != null
    }

    override fun findProject(uid: UUID): CromProject? = projectManager.findProject(uid)

    override fun findProject(projectName: String): CromProject? = projectManager.findProject(projectName)

    override fun createProject(projectName: String): CromProject {
        val project = projectManager.createProject(projectName)
        permissionService.registerProject(project)
        return project
    }
}
