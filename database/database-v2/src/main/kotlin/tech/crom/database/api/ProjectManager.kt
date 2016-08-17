package tech.crom.database.api

import tech.crom.model.project.CromProject
import java.util.*

interface ProjectManager {

    fun findProject(uid: UUID): CromProject?

    fun findProject(projectName: String): CromProject?

    @Throws(ProjectAlreadyExistsException::class)
    fun createProject(name: String): CromProject

    class ProjectAlreadyExistsException(name: String): RuntimeException("Project $name already exists")
}
