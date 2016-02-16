package io.ehdev.conrad.authentication.api

import io.ehdev.conrad.authentication.config.TestConradSecurityConfig
import io.ehdev.conrad.db.Tables
import org.jooq.DSLContext
import org.pac4j.oauth.profile.github.GitHubAttributesDefinition
import org.pac4j.oauth.profile.github.GitHubProfile
import org.pac4j.springframework.security.authentication.ClientAuthenticationToken
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification
import spock.lang.Unroll

@Rollback
@Transactional
@TestPropertySource(properties = ["api.verification = false"])
@ContextConfiguration(classes = [TestConradSecurityConfig], loader = SpringApplicationContextLoader)
class DefaultAuthenticationManagementApiTest extends Specification {

    @Autowired
    DefaultAuthenticationManagementApi authenticationManagementApi

    @Autowired
    DSLContext dslContext;

    @Unroll
    def 'will create profile (#type) when required'() {
        when:
        def authentication = authenticationManagementApi.findAuthentication(new ClientAuthenticationToken(null, 'clientName', profile, []))
        def into = dslContext.select().from(Tables.USER_DETAILS).fetchOne().into(Tables.USER_DETAILS)

        then:
        into.emailAddress == 'bob@joes.com'
        into.name == 'bob joe'
        into.userName == 'bobjoes'
        authentication.authenticated
        noExceptionThrown()

        when:
        authenticationManagementApi.findAuthentication(new ClientAuthenticationToken(null, 'clientName', profile, []))
        def size = dslContext.selectCount().from(Tables.USER_DETAILS).fetchOne().value1()

        then:
        size == 1

        where:
        type                           | profile
        GitHubProfile.getSimpleName()  | createGithubProfile()
    }

    def createGithubProfile() {
        def profile = new GitHubProfile()
        def properties = [(GitHubAttributesDefinition.NAME): 'bob joe', (GitHubAttributesDefinition.EMAIL): 'bob@joes.com', (GitHubAttributesDefinition.LOGIN): 'bobjoes']
        profile.build(1234, properties)

        return profile
    }
}
