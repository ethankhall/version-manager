package io.ehdev.conrad.database.config;

import io.ehdev.conrad.db.tables.daos.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConradDoaConfig {

    @Bean
    CommitDetailsDao commitDetailsDao(org.jooq.Configuration configuration) {
        return new CommitDetailsDao(configuration);
    }

    @Bean
    CommitMetadataDao commitMetadataDao(org.jooq.Configuration configuration) {
        return new CommitMetadataDao(configuration);
    }

    @Bean
    ProjectDetailsDao projectDetailsDao(org.jooq.Configuration configuration) {
        return new ProjectDetailsDao(configuration);
    }

    @Bean
    RepoDetailsDao repoDetailsDao(org.jooq.Configuration configuration) {
        return new RepoDetailsDao(configuration);
    }

    @Bean
    UserDetailsDao userDetailsDao(org.jooq.Configuration configuration) {
        return new UserDetailsDao(configuration);
    }

    @Bean
    TokenAuthenticationsDao tokenDao(org.jooq.Configuration configuration) {
        return new TokenAuthenticationsDao(configuration);
    }

    @Bean
    TokenJoinDao tokeMapDao(org.jooq.Configuration configuration) {
        return new TokenJoinDao(configuration);
    }

    @Bean
    VersionBumpersDao versionBumperDao(org.jooq.Configuration configuration) {
        return new VersionBumpersDao(configuration);
    }
}
