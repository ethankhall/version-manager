package io.ehdev.version.model;


import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableTransactionManagement
@EntityScan(basePackages = {"io.ehdev.version"})
@ComponentScan(basePackages = {"io.ehdev.version.model"})
@EnableJpaRepositories(basePackages = {"io.ehdev.version.model"})
public class ModelConfiguration {
}
