package tech.crom.business.api

import tech.crom.model.project.CromProject
import java.util.*

interface ProjectApi {
    fun createProject(projectName: String): CromProject

    fun findProject(uid: UUID): CromProject?

    fun findProject(projectName: String): CromProject?

    fun projectExists(projectName: String): Boolean
}
