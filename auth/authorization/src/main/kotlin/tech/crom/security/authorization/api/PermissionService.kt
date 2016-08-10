package tech.crom.security.authorization.api

import tech.crom.model.project.CromProject
import tech.crom.model.repository.CromRepo
import tech.crom.model.security.authorization.CromPermission

interface PermissionService {

    fun registerProject(cromProject: CromProject)

    fun registerRepository(cromRepo: CromRepo)

    fun hasAccessTo(cromRepo: CromRepo, accessLevel: CromPermission): Boolean

    fun hasAccessTo(cromProject: CromProject, accessLevel: CromPermission): Boolean
}
