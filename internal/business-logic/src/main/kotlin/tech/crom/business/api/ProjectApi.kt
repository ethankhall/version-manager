package tech.crom.business.api

import tech.crom.database.api.ProjectManager
import tech.crom.model.project.CromProject
import tech.crom.model.project.FilteredProjects
import tech.crom.model.user.CromUser
import javax.transaction.Transactional

@Transactional
interface ProjectApi {

    @Throws(ProjectManager.TooManyProjectsException::class, ProjectManager.ProjectAlreadyExistsException::class)
    fun createProject(cromUser: CromUser, projectName: String): CromProject

    fun findProject(id: Long): CromProject?

    fun findProject(projectName: String): CromProject?

    fun projectExists(projectName: String): Boolean

    fun findProjects(offset: Int, size: Int): FilteredProjects

    fun deleteProject(project: CromProject)
}
