package tech.crom.service.api.project

import io.ehdev.conrad.model.permission.PermissionCreateResponse
import io.ehdev.conrad.model.permission.PermissionGrant
import io.ehdev.conrad.service.api.aop.annotation.AdminPermissionRequired
import io.ehdev.conrad.service.api.aop.annotation.LoggedInUserRequired
import io.ehdev.conrad.service.api.aop.annotation.ProjectRequired
import io.ehdev.conrad.service.api.service.annotation.InternalLink
import io.ehdev.conrad.service.api.service.annotation.InternalLinks
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
class ProjectPermissionEndpoint @Autowired constructor(
    val permissionApi: PermissionApi
) {

    @ProjectRequired
    @LoggedInUserRequired
    @AdminPermissionRequired
    @RequestMapping(value = "/{username}", method = arrayOf(RequestMethod.DELETE))
    fun deletePermissions(container: RequestDetails, @PathVariable("username") username: String): ResponseEntity<Any> {
        permissionApi.dropPermission(username, container.cromProject!!)
        return ResponseEntity(HttpStatus.OK)
    }

    @ProjectRequired
    @InternalLinks(links = arrayOf(
        InternalLink(name = "project", ref = "/..")
    ))
    @LoggedInUserRequired
    @AdminPermissionRequired
    @RequestMapping(method = arrayOf(RequestMethod.POST))
    fun addPermission(container: RequestDetails, @RequestBody permissionGrant: PermissionGrant): ResponseEntity<PermissionCreateResponse> {
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
}
