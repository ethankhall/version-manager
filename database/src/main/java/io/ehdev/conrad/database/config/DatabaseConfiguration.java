package io.ehdev.conrad.database.config;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan("io.ehdev.conrad.database")
@EnableJpaRepositories("io.ehdev.conrad.database")
@EntityScan(basePackages = {"io.ehdev.conrad.database"})
public class DatabaseConfiguration {
}
