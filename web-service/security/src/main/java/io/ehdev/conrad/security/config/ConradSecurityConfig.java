package io.ehdev.conrad.security.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("io.ehdev.conrad.security")
@Import({SecurityDatabaseConfig.class, ConradWebSecurityConfig.class, ConradSocialConfig.class})
public class ConradSecurityConfig {

}
