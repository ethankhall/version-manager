package io.ehdev.conrad.service.api.config;

import io.ehdev.conrad.authentication.jwt.config.ConradJwtConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAspectJAutoProxy
@Import({ApiServiceWebMvcConfigurer.class, ConradJwtConfig.class})
@ComponentScan("io.ehdev.conrad.service.api")
public class ConradProjectApiConfiguration {

}
