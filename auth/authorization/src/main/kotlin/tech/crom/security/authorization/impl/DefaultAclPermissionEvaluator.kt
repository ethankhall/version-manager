package tech.crom.security.authorization.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.PermissionEvaluator
import org.springframework.security.acls.domain.DefaultPermissionFactory
import org.springframework.security.acls.domain.ObjectIdentityRetrievalStrategyImpl
import org.springframework.security.acls.domain.SidRetrievalStrategyImpl
import org.springframework.security.acls.model.MutableAclService
import org.springframework.security.acls.model.NotFoundException
import org.springframework.security.acls.model.ObjectIdentity
import org.springframework.security.acls.model.Permission
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import tech.crom.logger.logger
import java.io.Serializable
import java.util.*

@Service
class DefaultAclPermissionEvaluator @Autowired constructor(
    val aclService: MutableAclService) : PermissionEvaluator {

    private val log by logger()

    private val objectIdentityRetrievalStrategy = ObjectIdentityRetrievalStrategyImpl();
    private val objectIdentityGenerator = ObjectIdentityRetrievalStrategyImpl();
    private val sidRetrievalStrategy = SidRetrievalStrategyImpl();
    private val permissionFactory = DefaultPermissionFactory();

    override fun hasPermission(authentication: Authentication, domainObject: Any?, permission: Any?): Boolean {
        domainObject ?: return false
        val objectIdentity = objectIdentityRetrievalStrategy.getObjectIdentity(domainObject)
        return checkPermission(authentication, objectIdentity, permission)
    }

    override fun hasPermission(authentication: Authentication, targetId: Serializable?, targetType: String?, permission: Any?): Boolean {
        val objectIdentity = objectIdentityGenerator.createObjectIdentity(targetId, targetType);
        return checkPermission(authentication, objectIdentity, permission)
    }

    private fun checkPermission(authentication: Authentication, oid: ObjectIdentity, permission: Any?): Boolean {
        val requiredPermission = resolvePermission(permission)
        val sids = sidRetrievalStrategy.getSids(authentication);

        log.debug("Checking permission '$permission' for object '$oid'")

        val acl = aclService.readAclById(oid, sids)

        try {
            if (acl.isGranted(requiredPermission, sids, false)) {
                log.debug("Access is granted");
                return true;
            } else {
                log.debug("Returning false - ACLs returned, but insufficient permissions for this principal")
            }
        } catch (nfe: NotFoundException) {
            log.debug("Returning false - no ACLs apply for this principal");
        }

        return false
    }

    private fun resolvePermission(permission: Any?): List<Permission> {
        permission ?: return emptyList()

        if (permission is Int) {
            return Arrays.asList(permissionFactory.buildFromMask(permission))
        }

        if (permission is Permission) {
            return listOf(permission)
        }

        if (permission is Array<*>) {
            return permission.filter { it is Permission }.toList() as List<Permission>
        }

        if (permission is String) {
            val p: Permission

            try {
                p = permissionFactory.buildFromName(permission);
            } catch (notfound: IllegalArgumentException) {
                p = permissionFactory.buildFromName(permission.toUpperCase(Locale.ENGLISH));
            }

            return Arrays.asList(p);

        }
        throw IllegalArgumentException("Unsupported permission: " + permission);
    }
}
