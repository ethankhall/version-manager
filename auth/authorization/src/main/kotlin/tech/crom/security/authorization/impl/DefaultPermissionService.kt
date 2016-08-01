package tech.crom.security.authorization.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.acls.domain.BasePermission
import org.springframework.security.acls.domain.ObjectIdentityImpl
import org.springframework.security.acls.domain.PrincipalSid
import org.springframework.security.acls.model.MutableAcl
import org.springframework.security.acls.model.MutableAclService
import org.springframework.security.acls.model.NotFoundException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import tech.crom.database.api.ProjectManager
import tech.crom.model.project.CromProject
import tech.crom.model.repository.CromRepo
import tech.crom.security.authorization.api.PermissionService

@Service
class DefaultPermissionService @Autowired constructor(
    val aclService: MutableAclService,
    val projectManager: ProjectManager
): PermissionService {

    override fun registerRepository(cromRepo: CromRepo) {
        val oi = ObjectIdentityImpl(CromRepo::class.java, cromRepo.securityId);

        val acl: MutableAcl
        try {
            acl = aclService.readAclById(oi) as MutableAcl;
        } catch (nfe: NotFoundException) {
            acl = aclService.createAcl(oi);
        }

        val project = projectManager.findProject(cromRepo.projectUid) ?: throw IllegalStateException("Unable to find parent project")
        val projectAcl = aclService.readAclById(ObjectIdentityImpl(CromProject::class.java, project.securityId))

        acl.setParent(projectAcl)

        aclService.updateAcl(acl);

        throw UnsupportedOperationException()
    }

    override fun registerProject(cromProject: CromProject) {
        val oi = ObjectIdentityImpl(CromProject::class.java, cromProject.securityId);

        val acl: MutableAcl
        try {
            acl = aclService.readAclById(oi) as MutableAcl;
        } catch (nfe: NotFoundException) {
            acl = aclService.createAcl(oi);
        }

        val auth = SecurityContextHolder.getContext().authentication
        val sid = PrincipalSid(auth)

        acl.insertAce(acl.entries.size, BasePermission.ADMINISTRATION, sid, true);
        aclService.updateAcl(acl);
    }
}
