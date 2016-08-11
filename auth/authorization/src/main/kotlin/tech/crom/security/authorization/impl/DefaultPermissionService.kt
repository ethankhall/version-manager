package tech.crom.security.authorization.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.acls.domain.BasePermission
import org.springframework.security.acls.domain.ObjectIdentityImpl
import org.springframework.security.acls.domain.PrincipalSid
import org.springframework.security.acls.model.*
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import tech.crom.database.api.ProjectManager
import tech.crom.logger.getLogger
import tech.crom.model.project.CromProject
import tech.crom.model.repository.CromRepo
import tech.crom.model.security.authentication.CromUserAuthentication
import tech.crom.model.security.authorization.AuthorizedObject
import tech.crom.model.security.authorization.CromPermission
import tech.crom.model.user.CromUser
import tech.crom.security.authorization.api.PermissionService

@Service
class DefaultPermissionService @Autowired constructor(
    val aclService: MutableAclService,
    val projectManager: ProjectManager
) : PermissionService {

    companion object {
        val logger by getLogger()
    }

    override fun findHighestPermission(authorizedObject: AuthorizedObject): CromPermission {
        logger.info("Finding highest permission for on ${authorizedObject.getId()}")
        val oi = ObjectIdentityImpl(authorizedObject.javaClass, authorizedObject.getId())

        val permissions = mutableListOf<CromPermission>()
        val sid = PrincipalSid(SecurityContextHolder.getContext().authentication)
        var acl: Acl? = aclService.readAclById(oi, listOf(sid))

        while(acl != null) {
            permissions.addAll(acl.entries.map { permissionToCromPermission(it.permission) })
            acl = acl.parentAcl
        }

        return permissions
            .sortedByDescending { it.permissionLevel }
            .firstOrNull() ?: CromPermission.NONE
    }

    override fun revokePermission(cromUser: CromUser, authorizedObject: AuthorizedObject, accessLevel: CromPermission) {
        logger.info("Revoking $accessLevel from ${cromUser.userUid} on ${authorizedObject.getId()}")
        val oi = ObjectIdentityImpl(authorizedObject.javaClass, authorizedObject.getId())

        val sid = PrincipalSid(CromUserAuthentication(cromUser))
        val acl = aclService.readAclById(oi, listOf(sid)) as MutableAcl

        createPermissionGrantList(accessLevel).forEach { permission ->
            acl.entries.mapIndexed { index, accessControlEntry ->
                if (accessControlEntry.permission == permission && accessControlEntry.sid == sid) index else null
            }.filterNotNull().sortedDescending().forEach {
                acl.deleteAce(it)
            }
        }
        aclService.updateAcl(acl)
    }

    override fun grantPermission(cromUser: CromUser, authorizedObject: AuthorizedObject, accessLevel: CromPermission) {
        logger.info("Grating $accessLevel from ${cromUser.userUid} on ${authorizedObject.getId()}")
        val oi = ObjectIdentityImpl(authorizedObject.javaClass, authorizedObject.getId())

        val acl = aclService.readAclById(oi) as MutableAcl

        val sid = PrincipalSid(CromUserAuthentication(cromUser))

        createPermissionGrantList(accessLevel).forEach { permission ->
            acl.insertAce(acl.entries.size, permission, sid, true)
        }
        aclService.updateAcl(acl)
    }

    override fun hasAccessTo(cromUser: CromUser, authorizedObject: AuthorizedObject, accessLevel: CromPermission): Boolean {
        val oi = ObjectIdentityImpl(authorizedObject.javaClass, authorizedObject.getId())
        return validatePermissions(CromUserAuthentication(cromUser), accessLevel, oi)
    }

    override fun hasAccessTo(authorizedObject: AuthorizedObject, accessLevel: CromPermission): Boolean {
        val oi = ObjectIdentityImpl(authorizedObject.javaClass, authorizedObject.getId())
        return validatePermissions(SecurityContextHolder.getContext().authentication, accessLevel, oi)
    }

    private fun validatePermissions(auth: Authentication, accessLevel: CromPermission, oi: ObjectIdentityImpl): Boolean {
        val permission = createPermissionValidateList(accessLevel)

        val sid = PrincipalSid(auth)

        val acl: Acl = aclService.readAclById(oi)
        try {
            return acl.isGranted(permission, listOf(sid), false)
        } catch (nfe: NotFoundException) {
            return false
        }
    }

    override fun registerRepository(cromRepo: CromRepo) {
        logger.info("Registering repo ${cromRepo.repoUid} with security id ${cromRepo.securityId}")
        val oi = ObjectIdentityImpl(CromRepo::class.java, cromRepo.securityId)

        val acl: MutableAcl
        try {
            acl = aclService.readAclById(oi) as MutableAcl
        } catch (nfe: NotFoundException) {
            acl = aclService.createAcl(oi)
        }

        val project = projectManager.findProject(cromRepo.projectUid) ?: throw IllegalStateException("Unable to find parent project")
        val projectAcl = aclService.readAclById(ObjectIdentityImpl(CromProject::class.java, project.securityId))

        acl.setParent(projectAcl)

        aclService.updateAcl(acl)
    }

    override fun registerProject(cromProject: CromProject) {
        logger.info("Registering project ${cromProject.projectUid} with security id ${cromProject.securityId}")
        val oi = ObjectIdentityImpl(CromProject::class.java, cromProject.securityId)

        val acl: MutableAcl
        try {
            acl = aclService.readAclById(oi) as MutableAcl
        } catch (nfe: NotFoundException) {
            acl = aclService.createAcl(oi)
        }

        val auth = SecurityContextHolder.getContext().authentication
        val sid = PrincipalSid(auth)

        acl.insertAce(acl.entries.size, BasePermission.ADMINISTRATION, sid, true)
        acl.insertAce(acl.entries.size, BasePermission.WRITE, sid, true)
        acl.insertAce(acl.entries.size, BasePermission.READ, sid, true)
        aclService.updateAcl(acl)
    }

    internal fun permissionToCromPermission(permission: Permission): CromPermission {
        return when (permission) {
            BasePermission.ADMINISTRATION -> CromPermission.ADMIN
            BasePermission.WRITE -> CromPermission.WRITE
            BasePermission.READ -> CromPermission.READ
            else -> CromPermission.NONE
        }
    }

    internal fun createPermissionGrantList(permissionLevel: CromPermission): List<Permission> {
        return when (permissionLevel) {
            CromPermission.ADMIN -> listOf(BasePermission.ADMINISTRATION, BasePermission.WRITE, BasePermission.READ)
            CromPermission.WRITE -> listOf(BasePermission.READ, BasePermission.WRITE)
            CromPermission.READ -> listOf(BasePermission.READ)
            CromPermission.NONE -> listOf()
        }
    }

    internal fun createPermissionValidateList(permissionLevel: CromPermission): List<Permission> {
        return when (permissionLevel) {
            CromPermission.ADMIN -> listOf(BasePermission.ADMINISTRATION)
            CromPermission.WRITE -> listOf(BasePermission.ADMINISTRATION, BasePermission.WRITE)
            CromPermission.READ -> listOf(BasePermission.ADMINISTRATION, BasePermission.WRITE, BasePermission.READ)
            CromPermission.NONE -> listOf()
        }
    }
}
