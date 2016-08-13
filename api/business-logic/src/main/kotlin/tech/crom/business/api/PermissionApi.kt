package tech.crom.business.api

import tech.crom.model.security.authorization.AuthorizedObject
import tech.crom.model.security.authorization.CromPermission

interface PermissionApi {
    fun grantPermission(userName: String, authorizedObject: AuthorizedObject, permission: CromPermission): Boolean
    fun dropPermission(userName: String, authorizedObject: AuthorizedObject)
}
