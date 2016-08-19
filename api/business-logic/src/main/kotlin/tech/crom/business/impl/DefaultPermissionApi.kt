package tech.crom.business.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tech.crom.business.api.PermissionApi
import tech.crom.business.api.RepositoryApi
import tech.crom.database.api.UserManager
import tech.crom.model.project.CromProject
import tech.crom.model.repository.CromRepo
import tech.crom.model.security.authorization.AuthorizedObject
import tech.crom.model.security.authorization.CromPermission
import tech.crom.security.authorization.api.PermissionService

@Service
class DefaultPermissionApi @Autowired constructor(
    val permissionService: PermissionService,
    val userManager: UserManager,
    val repositoryApi: RepositoryApi
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

    override fun findAllPermissions(authorizedObject: AuthorizedObject): List<PermissionApi.PermissionGroup> {
        return permissionService
            .retrieveAllPermissions(authorizedObject)
            .groupBy { it.userId }
            .map {
                val permissions: CromPermission = it
                    .value
                    .sortedByDescending { it.permission.permissionLevel }
                    .firstOrNull()
                    ?.permission ?: CromPermission.NONE

                val user = userManager.findUserDetails(it.key)!!
                PermissionApi.PermissionGroup(user, permissions)
            }
    }

    override fun findHighestPermission(authorizedObject: AuthorizedObject): CromPermission {
        val foundPermission = permissionService.findHighestPermission(authorizedObject)
        if(authorizedObject is CromProject) {
            return if (foundPermission == CromPermission.NONE) CromPermission.READ else foundPermission
        }

        if(authorizedObject is CromRepo) {
            if(foundPermission == CromPermission.NONE) {
                val repoDetails = repositoryApi.getRepoDetails(authorizedObject)
                return if (repoDetails.public) CromPermission.READ else foundPermission
            } else {
                return foundPermission
            }
        }

        return foundPermission
    }

    override fun dropPermission(userName: String, authorizedObject: AuthorizedObject) {
        val cromUser = userManager.findUserDetails(userName) ?: return
        permissionService.revokePermission(cromUser, authorizedObject, CromPermission.ADMIN)
    }

    override fun getPermissions(authorizedObject: AuthorizedObject): List<PermissionApi.PermissionGroup> {
        return permissionService
            .retrieveAllPermissions(authorizedObject)
            .map { PermissionApi.PermissionGroup(userManager.findUserDetails(it.userId)!!, it.permission)}
            .toList()
    }
}
