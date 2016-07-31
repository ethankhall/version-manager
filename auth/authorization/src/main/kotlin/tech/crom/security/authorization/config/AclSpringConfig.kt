package tech.crom.security.authorization.config

import net.sf.ehcache.Ehcache
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.security.acls.domain.AclAuthorizationStrategy
import org.springframework.security.acls.domain.AclAuthorizationStrategyImpl
import org.springframework.security.acls.domain.DefaultPermissionGrantingStrategy
import org.springframework.security.acls.domain.EhCacheBasedAclCache
import org.springframework.security.acls.jdbc.BasicLookupStrategy
import org.springframework.security.acls.jdbc.JdbcMutableAclService
import org.springframework.security.acls.jdbc.LookupStrategy
import org.springframework.security.acls.model.AclCache
import org.springframework.security.acls.model.MutableAclService
import org.springframework.security.core.authority.SimpleGrantedAuthority
import tech.crom.config.SharedMasterConfig
import javax.sql.DataSource

@Configuration
@Import(SharedMasterConfig::class)
open class AclSpringConfig {

    @Bean
    open fun aclService(dataSource: DataSource, lookupStrategy: LookupStrategy, aclCache: AclCache): MutableAclService {
        //select currval(pg_get_serial_sequence('acl_class', 'id'))
        //select currval(pg_get_serial_sequence('acl_sid', 'id'))

        return JdbcMutableAclService(dataSource, lookupStrategy, aclCache)
    }

    @Bean
    open fun lookupStrategy(dataSource: DataSource, aclCache: AclCache): LookupStrategy {
        return BasicLookupStrategy(dataSource, aclCache, aclAuthorizationStrategyAdmin(), slf4jAuditLogger())
    }

    @Bean
    open fun aclAuthorizationStrategyAdmin(): AclAuthorizationStrategy {
        return AclAuthorizationStrategyImpl(SimpleGrantedAuthority("ROLE_ADMINISTRATOR"))
    }

    @Bean
    open fun aclAuthorizationStrategyAclAdmin(): AclAuthorizationStrategy {
        return AclAuthorizationStrategyImpl(SimpleGrantedAuthority("ROLE_ACL_ADMIN"))
    }

    @Bean
    open fun aclCache(ehCache: Ehcache): AclCache {
        val grantingStrategy = DefaultPermissionGrantingStrategy(slf4jAuditLogger())
        return EhCacheBasedAclCache(ehCache, grantingStrategy, aclAuthorizationStrategyAclAdmin())
    }

    @Bean
    open fun slf4jAuditLogger() = Slf4jAuditLogger()
}
