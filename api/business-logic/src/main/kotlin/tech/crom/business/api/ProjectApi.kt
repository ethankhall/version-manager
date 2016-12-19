package tech.crom.business.api

import tech.crom.model.project.CromProject
import tech.crom.model.project.FilteredProjects
import tech.crom.model.user.CromUser

interface ProjectApi {
    fun createProject(cromUser: CromUser, projectName: String): CromProject

    fun findProject(id: Long): CromProject?

    fun findProject(projectName: String): CromProject?

    fun projectExists(projectName: String): Boolean

    fun findProjects(offset: Int, size: Int): FilteredProjects

    fun deleteProject(project: CromProject)
}
