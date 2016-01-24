package io.ehdev.conrad.authentication.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan({"io.ehdev.conrad.authentication", "org.pac4j.springframework.web"})
@Import({ConradSecurityDatabaseConfig.class, ConradWebSecurityConfig.class})
public class ConradSecurityConfig extends WebMvcConfigurerAdapter {

}
