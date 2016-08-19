package io.ehdev.conrad.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
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

    public static void main(String[] args) throws Exception {
        SpringApplication.run(MainApplication.class, args);
    }
}
