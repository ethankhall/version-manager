package io.ehdev.conrad.app;

import io.ehdev.conrad.backend.config.BackendConfiguration;
import io.ehdev.conrad.database.config.DatabaseConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAutoConfiguration
@Import({BackendConfiguration.class, DatabaseConfiguration.class})
@ComponentScan("io.ehdev.conrad.app")
public class MainApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(MainApplication.class, args);
    }
}
