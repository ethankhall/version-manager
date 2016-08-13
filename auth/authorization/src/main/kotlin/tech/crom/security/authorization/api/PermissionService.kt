package tech.crom.security.authorization.api

import tech.crom.model.project.CromProject
import tech.crom.model.repository.CromRepo
import tech.crom.model.security.authorization.AuthorizedObject
import tech.crom.model.security.authorization.CromPermission
import tech.crom.model.user.CromUser

interface PermissionService {

    fun registerProject(cromProject: CromProject)

    fun registerRepository(cromRepo: CromRepo)

    fun hasAccessTo(authorizedObject: AuthorizedObject, accessLevel: CromPermission): Boolean

    fun hasAccessTo(cromUser: CromUser, authorizedObject: AuthorizedObject, accessLevel: CromPermission): Boolean

    fun grantPermission(cromUser: CromUser, authorizedObject: AuthorizedObject, accessLevel: CromPermission)

    fun revokePermission(cromUser: CromUser, authorizedObject: AuthorizedObject, accessLevel: CromPermission)

    fun findHighestPermission(authorizedObject: AuthorizedObject): CromPermission

    fun remove(authorizedObject: AuthorizedObject)
}
