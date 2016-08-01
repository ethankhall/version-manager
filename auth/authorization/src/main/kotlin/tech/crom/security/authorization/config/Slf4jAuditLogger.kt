package tech.crom.security.authorization.config

import org.springframework.security.acls.domain.AuditLogger
import org.springframework.security.acls.model.AccessControlEntry
import org.springframework.security.acls.model.AuditableAccessControlEntry
import org.springframework.util.Assert
import tech.crom.logger.getLogger

class Slf4jAuditLogger : AuditLogger {

    val log by getLogger()

    override fun logIfNeeded(granted: Boolean, ace: AccessControlEntry?) {
        Assert.notNull(ace, "AccessControlEntry required")

        if (ace is AuditableAccessControlEntry) {

            if (granted && ace.isAuditSuccess) {
                log.info("GRANTED due to ACE: " + ace)
            } else if (!granted && ace.isAuditFailure) {
                log.info("DENIED due to ACE: " + ace)
            }
        }
    }
}
