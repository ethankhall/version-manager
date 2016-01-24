package io.ehdev.conrad.database.api.internal

import io.ehdev.conrad.database.config.TestConradDatabaseConfig
import io.ehdev.conrad.database.impl.token.UserTokenModelRepository
import io.ehdev.conrad.model.user.ConradTokenType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

import javax.transaction.Transactional
import java.time.ZoneOffset
import java.time.ZonedDateTime

@Transactional
@ContextConfiguration(classes = [TestConradDatabaseConfig], loader = SpringApplicationContextLoader)
class DefaultTokenManagementApiTest extends Specification {

    @Autowired
    DefaultTokenManagementApi tokenManagementApi

    @Autowired
    UserTokenModelRepository tokenModelRepository

    @Autowired
    DefaultUserManagementApi userManagementApi

    def 'can create tokens'() {
        when:
        def user = userManagementApi.createUser('name', 'email')
        def token = tokenManagementApi.createToken(user, ConradTokenType.USER)

        then:
        token.uuid != null
        token.tokenType == ConradTokenType.USER
        tokenManagementApi.isTokenValid(token)
    }

    def 'will say token is invalid when it is expired'() {
        when:
        def user = userManagementApi.createUser('name', 'email')
        def token = tokenManagementApi.createToken(user, ConradTokenType.USER)
        def model = tokenModelRepository.getOne(token.uuid)
        model.expiresAt = ZonedDateTime.now(ZoneOffset.UTC).minusSeconds(1)
        tokenModelRepository.save(model)

        then:
        !tokenManagementApi.isTokenValid(token)
    }

    def 'will say token is invalid not valid'() {
        when:
        def user = userManagementApi.createUser('name', 'email')
        def token = tokenManagementApi.createToken(user, ConradTokenType.USER)
        tokenManagementApi.invalidateTokenValid(token)

        then:
        !tokenManagementApi.isTokenValid(token)
    }

    def 'can find user by token'() {
        when:
        def user = userManagementApi.createUser('name', 'email')
        def token = tokenManagementApi.createToken(user, ConradTokenType.USER)

        then:
        tokenManagementApi.findUser(token).uuid == user.uuid
    }
}
