package tech.crom.security.authorization.confg

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import tech.crom.config.ClockConfig
import tech.crom.config.DatabaseConfig
import tech.crom.database.config.CromDoaConfig
import tech.crom.security.authorization.config.AuthorizationConfig
import tech.crom.security.authorization.testdouble.StubAuthenticationManager

@Configuration
@Import([DatabaseConfig, ClockConfig, CromDoaConfig, AuthorizationConfig, StubAuthenticationManager])
class TestConfig {
}
