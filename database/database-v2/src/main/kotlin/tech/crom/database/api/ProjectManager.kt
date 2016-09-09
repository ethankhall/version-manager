package tech.crom.database.api

import tech.crom.model.project.CromProject
import tech.crom.model.project.FilteredProjects
import tech.crom.model.user.CromUser
import java.util.*

interface ProjectManager {

    fun findProject(uid: UUID): CromProject?

    fun findProject(projectName: String): CromProject?

    @Throws(ProjectAlreadyExistsException::class)
    fun createProject(name: String): CromProject

    fun findProjects(offset: Int, size: Int): FilteredProjects

    class ProjectAlreadyExistsException(name: String): RuntimeException("Project $name already exists")
}
