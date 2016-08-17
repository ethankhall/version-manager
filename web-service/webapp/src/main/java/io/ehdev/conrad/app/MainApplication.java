package io.ehdev.conrad.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.kotlin.KotlinModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;
import tech.crom.business.config.BuisnessLogicConfig;
import tech.crom.config.ClockConfig;
import tech.crom.config.DatabaseConfig;
import tech.crom.config.SharedMasterConfig;
import tech.crom.config.SpringTransactionProvider;
import tech.crom.security.authentication.config.AuthenticationConfig;
import tech.crom.security.authorization.config.AuthorizationConfig;
import tech.crom.service.api.config.ServiceApiConfig;
import tech.crom.version.bumper.config.VersionBumperConfig;

@EnableCaching
@Configuration
@Import({DatabaseConfig.class, ClockConfig.class, SharedMasterConfig.class, SpringTransactionProvider.class,
    VersionBumperConfig.class, BuisnessLogicConfig.class, AuthorizationConfig.class, AuthenticationConfig.class,
    ServiceApiConfig.class})
@ComponentScan("io.ehdev.conrad.app")
@EnableAutoConfiguration
public class MainApplication {

    @Bean
    @Primary
    public ObjectMapper myObjectMapper() {
        return new ObjectMapper()
            .registerModule(new KotlinModule())
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }


    public static void main(String[] args) throws Exception {
        SpringApplication.run(MainApplication.class, args);
    }
}
