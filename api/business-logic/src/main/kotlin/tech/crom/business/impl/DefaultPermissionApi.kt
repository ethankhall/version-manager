package tech.crom.business.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tech.crom.business.api.PermissionApi
import tech.crom.database.api.UserManager
import tech.crom.model.security.authorization.AuthorizedObject
import tech.crom.model.security.authorization.CromPermission
import tech.crom.security.authorization.api.PermissionService

@Service
class DefaultPermissionApi @Autowired constructor(
    val permissionService: PermissionService,
    val userManager: UserManager
): PermissionApi {
    override fun grantPermission(userName: String, authorizedObject: AuthorizedObject, permission: CromPermission): Boolean {
        try {
            val cromUser = userManager.findUserDetails(userName) ?: return false
            permissionService.grantPermission(cromUser, authorizedObject, permission)
            return true
        } catch (e: Exception) {
            return false
        }
    }

    override fun dropPermission(userName: String, authorizedObject: AuthorizedObject) {
        val cromUser = userManager.findUserDetails(userName) ?: return
        permissionService.revokePermission(cromUser, authorizedObject, CromPermission.ADMIN)
    }
}
