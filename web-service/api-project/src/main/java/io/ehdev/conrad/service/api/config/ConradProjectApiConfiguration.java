package io.ehdev.conrad.service.api.config;

import com.fasterxml.jackson.module.kotlin.KotlinModule;
import io.ehdev.conrad.authentication.jwt.config.ConradJwtConfig;
import org.springframework.context.annotation.*;

@Configuration
@EnableAspectJAutoProxy
@Import({ApiServiceWebMvcConfigurer.class, ConradJwtConfig.class})
@ComponentScan("io.ehdev.conrad.service.api")
public class ConradProjectApiConfiguration {

    @Bean
    public KotlinModule kotlinModule() {
        return new KotlinModule();
    }
}
