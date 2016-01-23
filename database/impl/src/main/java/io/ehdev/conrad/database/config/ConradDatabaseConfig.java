package io.ehdev.conrad.database.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ConradJpaConfig.class})
@ComponentScan({"io.ehdev.conrad.database"})
public class ConradDatabaseConfig {
}
