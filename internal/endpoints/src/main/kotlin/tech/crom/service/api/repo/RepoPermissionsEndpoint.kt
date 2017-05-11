package tech.crom.service.api.repo

import io.ehdev.conrad.service.api.aop.annotation.AdminPermissionRequired
import io.ehdev.conrad.service.api.aop.annotation.LoggedInUserRequired
import io.ehdev.conrad.service.api.aop.annotation.RepoRequired
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import tech.crom.business.api.PermissionApi
import tech.crom.model.security.authorization.CromPermission
import tech.crom.rest.model.permission.PermissionCreateResponse
import tech.crom.rest.model.permission.PermissionGrant
import tech.crom.web.api.model.RequestDetails

@Service
@RequestMapping("/api/v1/project/{projectName}/repo/{repoName}/permissions")
open class RepoPermissionsEndpoint @Autowired constructor(
    val permissionApi: PermissionApi
) {

    @RepoRequired
    @LoggedInUserRequired
    @AdminPermissionRequired
    @RequestMapping(value = "/{username}", method = arrayOf(RequestMethod.DELETE))
    open fun deletePermissions(container: RequestDetails, @PathVariable("username") username: String): ResponseEntity<Any> {
        permissionApi.dropPermission(username, container.cromRepo!!)
        return ResponseEntity(HttpStatus.OK)
    }

    @RepoRequired
    @LoggedInUserRequired
    @AdminPermissionRequired
    @RequestMapping(method = arrayOf(RequestMethod.POST))
    open fun addPermission(container: RequestDetails, @RequestBody permissionGrant: PermissionGrant): ResponseEntity<PermissionCreateResponse> {
        if (permissionApi.grantPermission(permissionGrant.username, container.cromRepo!!, convertType(permissionGrant))) {
            return ResponseEntity(PermissionCreateResponse(true), HttpStatus.CREATED)
        } else {
            return ResponseEntity(PermissionCreateResponse(false), HttpStatus.FORBIDDEN)
        }
    }

    private fun convertType(permission: PermissionGrant): CromPermission {
        return when (permission.accessLevel) {
            PermissionGrant.AccessLevel.NONE -> CromPermission.NONE
            PermissionGrant.AccessLevel.READ -> CromPermission.READ
            PermissionGrant.AccessLevel.WRITE -> CromPermission.WRITE
            PermissionGrant.AccessLevel.ADMIN -> CromPermission.ADMIN
        }
    }
}
