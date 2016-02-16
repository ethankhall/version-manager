package io.ehdev.conrad.authentication.config

import io.ehdev.conrad.authentication.ConradExternalAuth
import io.ehdev.conrad.authentication.jwt.config.ConradJwtConfig
import io.ehdev.conrad.database.config.ConradDatabaseConfig
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import([ConradSecurityConfig, ConradJwtConfig, ConradDatabaseConfig, ConradExternalAuth])
class TestConradSecurityConfig {
}
