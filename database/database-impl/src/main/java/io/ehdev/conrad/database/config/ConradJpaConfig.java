package io.ehdev.conrad.database.config;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan("io.ehdev.conrad.database.impl")
@EnableJpaRepositories({"io.ehdev.conrad.database.impl"})
public class ConradJpaConfig {
}
