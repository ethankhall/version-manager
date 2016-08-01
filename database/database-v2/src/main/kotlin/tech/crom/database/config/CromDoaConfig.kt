package tech.crom.database.config

import io.ehdev.conrad.db.tables.daos.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class CromDoaConfig {

    @Bean
    open fun commitDetailsDao(configuration: org.jooq.Configuration): CommitDetailsDao {
        return CommitDetailsDao(configuration);
    }

    @Bean
    open fun commitMetadataDao(configuration: org.jooq.Configuration): CommitMetadataDao {
        return CommitMetadataDao(configuration);
    }

    @Bean
    open fun projectDetailsDao(configuration: org.jooq.Configuration): ProjectDetailsDao {
        return ProjectDetailsDao(configuration);
    }

    @Bean
    open fun repoDetailsDao(configuration: org.jooq.Configuration): RepoDetailsDao {
        return RepoDetailsDao(configuration);
    }

    @Bean
    open fun userDetailsDao(configuration: org.jooq.Configuration): UserDetailsDao {
        return UserDetailsDao(configuration);
    }

    @Bean
    open fun versionBumperDao(configuration: org.jooq.Configuration): VersionBumpersDao {
        return VersionBumpersDao(configuration);
    }
}
