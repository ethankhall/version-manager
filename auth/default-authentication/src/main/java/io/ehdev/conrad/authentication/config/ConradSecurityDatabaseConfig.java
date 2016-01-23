package io.ehdev.conrad.authentication.config;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan("io.ehdev.conrad.authentication.database")
@EnableJpaRepositories({"io.ehdev.conrad.authentication.database"})
public class ConradSecurityDatabaseConfig {
}
