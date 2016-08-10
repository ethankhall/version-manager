package tech.crom.security.authorization.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.acls.domain.BasePermission
import org.springframework.security.acls.domain.ObjectIdentityImpl
import org.springframework.security.acls.domain.PrincipalSid
import org.springframework.security.acls.model.*
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import tech.crom.database.api.ProjectManager
import tech.crom.logger.getLogger
import tech.crom.model.project.CromProject
import tech.crom.model.repository.CromRepo
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

    internal fun convertPermissionLevel(permissionLevel: CromPermission): Permission {
        return when (permissionLevel) {
            CromPermission.ADMIN -> BasePermission.ADMINISTRATION
            CromPermission.READ -> BasePermission.READ
            CromPermission.WRITE -> BasePermission.WRITE
        }
    }

    override fun hasAccessTo(cromRepo: CromRepo, accessLevel: CromPermission): Boolean {
        val oi = ObjectIdentityImpl(CromRepo::class.java, cromRepo.securityId)
        return validatePermissions(accessLevel, oi)
    }

    override fun hasAccessTo(cromProject: CromProject, accessLevel: CromPermission): Boolean {
        val oi = ObjectIdentityImpl(CromProject::class.java, cromProject.securityId)
        return validatePermissions(accessLevel, oi)
    }

    private fun validatePermissions(accessLevel: CromPermission, oi: ObjectIdentityImpl): Boolean {
        val permission = convertPermissionLevel(accessLevel)

        val auth = SecurityContextHolder.getContext().authentication
        val sid = PrincipalSid(auth)

        val acl: Acl = aclService.readAclById(oi)
        try {
            return acl.isGranted(listOf(permission), listOf(sid), false)
        } catch (nfe: NotFoundException) {
            return false
        }
    }

    override fun registerRepository(cromRepo: CromRepo) {
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
}
