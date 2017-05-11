package tech.crom.business.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import tech.crom.business.api.CommitApi
import tech.crom.business.api.RepositoryApi
import tech.crom.business.api.VersionBumperApi
import tech.crom.config.ClockConfig
import tech.crom.config.DatabaseConfig
import tech.crom.database.api.*
import tech.crom.database.config.CromDoaConfig
import tech.crom.security.authorization.api.PermissionService

@Configuration
@Import([DatabaseConfig, ClockConfig, CromDoaConfig])
class CommitApiConfiguration {

    @Bean
    @Autowired
    VersionBumperApi versionBumperApi(VersionBumperManager versionBumperManager) {
        new DefaultVersionBumperApi(versionBumperManager)
    }

    @Bean
    @Autowired
    CommitApi commitApi(CommitManager commitManager, StateMachineManager stateMachineManager,
                        CacheManager cacheManager, VersionBumperApi versionBumperApi) {
        new DefaultCommitApi(commitManager, stateMachineManager, cacheManager, versionBumperApi)
    }

    @Bean
    @Autowired
    RepositoryApi repositoryApi(RepoManager repoManager, VersionBumperManager versionBumperManager,
                                StateMachineManager stateMachineManager, CacheManager cacheManager) {
        def service = ['registerRepository': { it -> }] as PermissionService
        new DefaultRepositoryApi(repoManager, versionBumperManager, stateMachineManager, service, cacheManager)
    }
}
