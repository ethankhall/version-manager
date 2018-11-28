package tech.crom.business.api

import tech.crom.model.security.authorization.CromPermission
import tech.crom.model.user.CromUser

interface PermissionApi {
    fun grantPermission(userName: String, authorizedObject: Any, permission: CromPermission): Boolean
    fun dropPermission(userName: String, authorizedObject: Any)
    fun getPermissions(authorizedObject: Any): List<PermissionGroup>
    fun findAllPermissions(authorizedObject: Any): List<PermissionGroup>
    fun findHighestPermission(authorizedObject: Any): CromPermission

    data class PermissionGroup(val cromUser: CromUser, val cromPermission: CromPermission)

}
