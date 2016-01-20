package io.ehdev.conrad.api.user.config;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan("io.ehdev.conrad.api.user.database")
@EnableJpaRepositories({"io.ehdev.conrad.api.user.database"})
public class ApiUserDatabaseConfig {
}
