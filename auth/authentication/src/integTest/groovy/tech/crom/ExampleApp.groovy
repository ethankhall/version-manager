package tech.crom

import io.ehdev.conrad.database.config.ConradDatabaseConfig
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import tech.crom.security.authentication.config.SpringSocialConfig
import tech.crom.security.authentication.config.WebSecurityConfig

@Configuration
@ComponentScan
@EnableAutoConfiguration
@Import([ConradDatabaseConfig, SpringSocialConfig, WebSecurityConfig])
class ExampleApp {
    static main(args) {
        SpringApplication.run(ExampleApp, '--debug')
    }
}
