package io.ehdev.version;

import io.ehdev.version.model.ModelConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@EnableTransactionManagement
@Import(ModelConfiguration.class)
@EntityScan(basePackages = {"io.ehdev.version"})
@ComponentScan(basePackages = {"io.ehdev.version"})
@EnableJpaRepositories(basePackages = {"io.ehdev.version"})
public class ApiTestConfiguration {

}
