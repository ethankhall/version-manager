package io.ehdev.conrad.security.config;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan("io.ehdev.conrad.security.database")
@EnableJpaRepositories({"io.ehdev.conrad.security.database"})
public class SecurityDatabaseConfig {
}
