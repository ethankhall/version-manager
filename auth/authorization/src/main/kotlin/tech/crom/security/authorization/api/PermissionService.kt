package tech.crom.security.authorization.api

import tech.crom.model.project.CromProject
import tech.crom.model.repository.CromRepo

interface PermissionService {

    fun registerProject(cromProject: CromProject)

    fun registerRepository(cromRepo: CromRepo)
}
