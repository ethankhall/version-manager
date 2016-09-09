package tech.crom.database.config

import io.ehdev.conrad.db.tables.daos.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class CromDoaConfig {
    @Bean
    open fun commitDetailsDao(config: org.jooq.Configuration): CommitDetailsDao = CommitDetailsDao(config)

    @Bean
    open fun commitMetadataDao(config: org.jooq.Configuration): CommitMetadataDao = CommitMetadataDao(config)

    @Bean
    open fun projectDetailsDao(config: org.jooq.Configuration): ProjectDetailsDao = ProjectDetailsDao(config)

    @Bean
    open fun repoDetailsDao(config: org.jooq.Configuration): RepoDetailsDao = RepoDetailsDao(config)

    @Bean
    open fun userDetailsDao(config: org.jooq.Configuration): UserDetailsDao = UserDetailsDao(config)

    @Bean
    open fun versionBumperDao(config: org.jooq.Configuration): VersionBumpersDao = VersionBumpersDao(config)

    @Bean
    open fun userTokensDao(config: org.jooq.Configuration): UserTokensDao = UserTokensDao(config)

    @Bean
    open fun repositoryTokensDao(config: org.jooq.Configuration): RepositoryTokensDao = RepositoryTokensDao(config)

    @Bean
    open fun watcherDao(config: org.jooq.Configuration): WatcherDao = WatcherDao(config)
}
