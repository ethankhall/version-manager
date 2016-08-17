package tech.crom.security.authorization.config

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(AclSpringConfig::class)
@ComponentScan("tech.crom.security.authorization")
open class AuthorizationConfig
