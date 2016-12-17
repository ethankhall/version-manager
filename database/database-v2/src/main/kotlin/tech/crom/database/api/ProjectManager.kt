package tech.crom.database.api

import tech.crom.model.project.CromProject
import tech.crom.model.project.FilteredProjects

interface ProjectManager {

    fun findProject(id: Long): CromProject?

    fun findProject(projectName: String): CromProject?

    @Throws(ProjectAlreadyExistsException::class)
    fun createProject(name: String): CromProject

    fun findProjects(offset: Int, size: Int): FilteredProjects

    fun deleteProject(project: CromProject)

    class ProjectAlreadyExistsException(name: String): RuntimeException("Project $name already exists")
}
