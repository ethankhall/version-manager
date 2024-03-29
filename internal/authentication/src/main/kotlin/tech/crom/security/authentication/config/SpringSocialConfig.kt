package tech.crom.security.authentication.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.core.env.Environment
import org.springframework.security.crypto.encrypt.Encryptors
import org.springframework.social.UserIdSource
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer
import org.springframework.social.config.annotation.EnableSocial
import org.springframework.social.config.annotation.SocialConfigurer
import org.springframework.social.connect.Connection
import org.springframework.social.connect.ConnectionFactoryLocator
import org.springframework.social.connect.ConnectionRepository
import org.springframework.social.connect.UsersConnectionRepository
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository
import org.springframework.social.google.api.Google
import org.springframework.social.google.connect.GoogleConnectionFactory
import org.springframework.social.security.AuthenticationNameUserIdSource
import tech.crom.security.authentication.service.CromConnectionSignupService
import javax.sql.DataSource

@EnableSocial
@Configuration
open class SpringSocialConfig @Autowired constructor(val ds: DataSource, val signUp: CromConnectionSignupService) : SocialConfigurer {

    override fun getUsersConnectionRepository(connectionFactoryLocator: ConnectionFactoryLocator?): UsersConnectionRepository? {
        val jdbcUsersConnectionRepository = JdbcUsersConnectionRepository(ds, connectionFactoryLocator, Encryptors.noOpText())
        jdbcUsersConnectionRepository.setTablePrefix("ss_")
        jdbcUsersConnectionRepository.setConnectionSignUp(signUp)
        return jdbcUsersConnectionRepository
    }

    override fun getUserIdSource(): UserIdSource = AuthenticationNameUserIdSource()

    override fun addConnectionFactories(cfConfigurer: ConnectionFactoryConfigurer, env: Environment) {
        val factory = GoogleConnectionFactory(
            env.getRequiredProperty("auth.client.google.client.id"),
            env.getRequiredProperty("auth.client.google.client.secret"))

        factory.scope = "email"
        cfConfigurer.addConnectionFactory(factory)
    }

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
    open fun google(repository: ConnectionRepository): Google? {
        val connection: Connection<Google>? = repository.findPrimaryConnection(Google::class.java)
        return connection?.api
    }
}
