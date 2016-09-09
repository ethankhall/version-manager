package tech.crom.business.api

import tech.crom.model.project.CromProject
import tech.crom.model.project.FilteredProjects
import tech.crom.model.user.CromUser
import java.util.*

interface ProjectApi {
    fun createProject(cromUser: CromUser, projectName: String): CromProject

    fun findProject(uid: UUID): CromProject?

    fun findProject(projectName: String): CromProject?

    fun projectExists(projectName: String): Boolean

    fun findProjects(offset: Int, size: Int): FilteredProjects
}
