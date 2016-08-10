package tech.crom.security.authentication.config

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(WebSecurityConfig::class, SpringSocialConfig::class)
@ComponentScan("tech.crom.security.authentication")
open class AuthenticationConfig
