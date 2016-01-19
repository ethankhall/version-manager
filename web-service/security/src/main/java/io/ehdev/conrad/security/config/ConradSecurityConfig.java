package io.ehdev.conrad.security.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("io.ehdev.conrad.security")
@Import({ConradSecurityDatabaseConfig.class, ConradExternalAuth.class, ConradWebSecurityConfig.class})
public class ConradSecurityConfig {

}
