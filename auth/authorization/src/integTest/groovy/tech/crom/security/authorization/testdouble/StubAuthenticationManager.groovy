package tech.crom.security.authorization.testdouble

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
class StubAuthenticationManager extends WebSecurityConfigurerAdapter {

    @Override
    @Bean
    AuthenticationManager authenticationManagerBean() {
        return super.authenticationManagerBean()
    }
}
