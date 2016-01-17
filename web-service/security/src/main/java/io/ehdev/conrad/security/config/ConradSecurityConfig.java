package io.ehdev.conrad.security.config;

import io.ehdev.conrad.security.database.SecurityJpaConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("io.ehdev.conrad.security")
@Import({ConradWebSecurityConfig.class, ConradSocialConfig.class, SecurityJpaConfig.class})
public class ConradSecurityConfig {

}
