package io.ehdev.conrad.authentication.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("io.ehdev.conrad.authentication")
@Import({ConradSecurityDatabaseConfig.class, ConradExternalAuth.class, ConradWebSecurityConfig.class})
public class ConradSecurityConfig {

}
