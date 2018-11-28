package tech.crom.service.api.project

import io.ehdev.conrad.service.api.aop.annotation.AdminPermissionRequired
import io.ehdev.conrad.service.api.aop.annotation.LoggedInUserRequired
import io.ehdev.conrad.service.api.aop.annotation.ProjectRequired
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import tech.crom.business.api.PermissionApi
import tech.crom.model.security.authorization.CromPermission
import tech.crom.rest.model.permission.GetPermissionGrantWrapper
import tech.crom.rest.model.permission.PermissionCreateResponse
import tech.crom.rest.model.permission.PermissionGrant
import tech.crom.web.api.model.RequestDetails

@Controller
@RequestMapping(value = ["/api/v1/project/{projectName}/permissions"], produces = [MediaType.APPLICATION_JSON_VALUE])
class ProjectPermissionEndpoint @Autowired constructor(
    val permissionApi: PermissionApi
) {

    @ProjectRequired
    @LoggedInUserRequired
    @AdminPermissionRequired
    @RequestMapping(value = ["/{username}"], method = [RequestMethod.DELETE])
    fun deletePermissions(container: RequestDetails, @PathVariable("username") username: String): ResponseEntity<Any> {
        permissionApi.dropPermission(username, container.cromProject!!)
        return ResponseEntity(HttpStatus.OK)
    }

    @ProjectRequired
    @LoggedInUserRequired
    @AdminPermissionRequired
    @RequestMapping(method = [RequestMethod.GET])
    fun findPermissions(container: RequestDetails): ResponseEntity<GetPermissionGrantWrapper> {
        val permissions = permissionApi
            .findAllPermissions(container.cromProject!!)
            .map { PermissionGrant(it.cromUser.userName, convertType(it.cromPermission)) }
            .toList()

        return ResponseEntity(GetPermissionGrantWrapper(permissions), HttpStatus.OK)
    }

    @ProjectRequired
    @LoggedInUserRequired
    @AdminPermissionRequired
    @RequestMapping(method = [RequestMethod.POST])
    fun addPermission(container: RequestDetails, @RequestBody permissionGrant: PermissionGrant): ResponseEntity<PermissionCreateResponse> {
        return if (permissionApi.grantPermission(permissionGrant.username, container.cromProject!!, convertType(permissionGrant))) {
            ResponseEntity(PermissionCreateResponse(true), HttpStatus.CREATED)
        } else {
            ResponseEntity(PermissionCreateResponse(false), HttpStatus.FORBIDDEN)
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

    private fun convertType(permission: CromPermission): PermissionGrant.AccessLevel {
        return when (permission) {
            CromPermission.NONE -> PermissionGrant.AccessLevel.NONE
            CromPermission.READ -> PermissionGrant.AccessLevel.READ
            CromPermission.WRITE -> PermissionGrant.AccessLevel.WRITE
            CromPermission.ADMIN -> PermissionGrant.AccessLevel.ADMIN
        }
    }
}
