package tech.crom.business.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tech.crom.business.api.ProjectApi
import tech.crom.database.api.ProjectManager
import tech.crom.database.api.ProjectTrackerManager
import tech.crom.database.api.WatcherManager
import tech.crom.model.project.CromProject
import tech.crom.model.project.FilteredProjects
import tech.crom.model.user.CromUser
import tech.crom.security.authorization.api.PermissionService
import javax.transaction.Transactional

@Service
@Transactional
open class DefaultProjectApi @Autowired constructor(
    val projectManager: ProjectManager,
    val permissionService: PermissionService,
    val watcherManager: WatcherManager,
    val projectTrackerManager: ProjectTrackerManager
) : ProjectApi {

    override fun deleteProject(project: CromProject) {
        projectTrackerManager.unlink(project)
        projectManager.deleteProject(project)
        permissionService.remove(project)
    }

    override fun findProjects(offset: Int, size: Int): FilteredProjects {
        val saneSize = Math.min(size, 100)
        return projectManager.findProjects(offset, saneSize)
    }

    override fun projectExists(projectName: String): Boolean {
        return projectManager.findProject(projectName) != null
    }

    override fun findProject(id: Long): CromProject? = projectManager.findProject(id)

    override fun findProject(projectName: String): CromProject? = projectManager.findProject(projectName)

    override fun createProject(cromUser: CromUser, projectName: String): CromProject {
        if (projectTrackerManager.count(cromUser) >= 10) {
            throw ProjectManager.TooManyProjectsException(cromUser.userName)
        }

        val project = projectManager.createProject(projectName)
        permissionService.registerProject(project)
        watcherManager.addWatch(cromUser, project)
        projectTrackerManager.link(project, cromUser)
        return project
    }
}
