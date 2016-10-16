package tech.crom.service.api.project

import io.ehdev.conrad.model.permission.GetPermissionGranWrapper
import io.ehdev.conrad.model.permission.PermissionCreateResponse
import io.ehdev.conrad.model.permission.PermissionGrant
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
import tech.crom.web.api.model.RequestDetails

@Controller
@RequestMapping(value = "/api/v1/project/{projectName}/permissions", produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
open class ProjectPermissionEndpoint @Autowired constructor(
    val permissionApi: PermissionApi
) {

    @ProjectRequired
    @LoggedInUserRequired
    @AdminPermissionRequired
    @RequestMapping(value = "/{username}", method = arrayOf(RequestMethod.DELETE))
    open fun deletePermissions(container: RequestDetails, @PathVariable("username") username: String): ResponseEntity<Any> {
        permissionApi.dropPermission(username, container.cromProject!!)
        return ResponseEntity(HttpStatus.OK)
    }

    @ProjectRequired
    @LoggedInUserRequired
    @AdminPermissionRequired
    @RequestMapping(method = arrayOf(RequestMethod.GET))
    open fun findPermissions(container: RequestDetails): ResponseEntity<GetPermissionGranWrapper> {
        val permissions = permissionApi
            .findAllPermissions(container.cromProject!!)
            .map { PermissionGrant(it.cromUser.userName, convertType(it.cromPermission))}
            .toList()

        return ResponseEntity(GetPermissionGranWrapper(permissions), HttpStatus.OK)
    }

    @ProjectRequired
    @LoggedInUserRequired
    @AdminPermissionRequired
    @RequestMapping(method = arrayOf(RequestMethod.POST))
    open fun addPermission(container: RequestDetails, @RequestBody permissionGrant: PermissionGrant): ResponseEntity<PermissionCreateResponse> {
        if(permissionApi.grantPermission(permissionGrant.username, container.cromProject!!, convertType(permissionGrant))) {
            return ResponseEntity(PermissionCreateResponse(true), HttpStatus.CREATED)
        } else {
            return ResponseEntity(PermissionCreateResponse(false), HttpStatus.FORBIDDEN)
        }
    }

    private fun convertType(permission: PermissionGrant): CromPermission {
        return when(permission.permission) {
            PermissionGrant.PermissionDefinition.NONE -> CromPermission.NONE
            PermissionGrant.PermissionDefinition.READ -> CromPermission.READ
            PermissionGrant.PermissionDefinition.WRITE -> CromPermission.WRITE
            PermissionGrant.PermissionDefinition.ADMIN -> CromPermission.ADMIN
        }
    }

    private fun convertType(permission: CromPermission): PermissionGrant.PermissionDefinition {
        return when(permission) {
            CromPermission.NONE -> PermissionGrant.PermissionDefinition.NONE
            CromPermission.READ -> PermissionGrant.PermissionDefinition.READ
            CromPermission.WRITE -> PermissionGrant.PermissionDefinition.WRITE
            CromPermission.ADMIN -> PermissionGrant.PermissionDefinition.ADMIN
        }
    }
}
