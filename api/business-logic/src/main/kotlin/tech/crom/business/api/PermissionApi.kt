package tech.crom.business.api

import tech.crom.model.security.authorization.AuthorizedObject
import tech.crom.model.security.authorization.CromPermission
import tech.crom.model.user.CromUser

interface PermissionApi {
    fun grantPermission(userName: String, authorizedObject: AuthorizedObject, permission: CromPermission): Boolean
    fun dropPermission(userName: String, authorizedObject: AuthorizedObject)
    fun getPermissions(authorizedObject: AuthorizedObject): List<PermissionGroup>

    data class PermissionGroup(val cromUser: CromUser, val cromPermission: CromPermission)
}
