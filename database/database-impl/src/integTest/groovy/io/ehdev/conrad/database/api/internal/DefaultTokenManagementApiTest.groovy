package io.ehdev.conrad.database.api.internal

import io.ehdev.conrad.database.config.TestConradDatabaseConfig
import io.ehdev.conrad.database.model.user.ApiTokenType
import io.ehdev.conrad.db.tables.daos.UserTokensDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

import java.time.Instant

@Rollback
@Transactional
@ContextConfiguration(classes = [TestConradDatabaseConfig], loader = SpringApplicationContextLoader)
class DefaultTokenManagementApiTest extends Specification {

    @Autowired
    DefaultTokenManagementApi tokenManagementApi

    @Autowired
    UserTokensDao userTokensDao;

    @Autowired
    DefaultUserManagementApi userManagementApi

    def 'can create tokens'() {
        when:
        def user = userManagementApi.createUser('userId', 'name', 'email')
        def token = tokenManagementApi.createToken(user, ApiTokenType.USER)

        then:
        token.uuid != null
        token.tokenType == ApiTokenType.USER
        tokenManagementApi.isTokenValid(token)
    }

    def 'will say token is invalid when it is expired'() {
        when:
        def user = userManagementApi.createUser('userId', 'name', 'email')
        def token = tokenManagementApi.createToken(user, ApiTokenType.USER)
        def model = userTokensDao.fetchOneByUuid(token.uuid)
        model.setExpiresAt(Instant.now().minusSeconds(1))
        userTokensDao.update(model)

        then:
        !tokenManagementApi.isTokenValid(token)
    }

    def 'will say token is invalid not valid'() {
        when:
        def user = userManagementApi.createUser('userId', 'name', 'email')
        def token = tokenManagementApi.createToken(user, ApiTokenType.USER)
        tokenManagementApi.invalidateTokenValid(token)

        then:
        !tokenManagementApi.isTokenValid(token)
    }

    def 'can find user by token'() {
        when:
        def user = userManagementApi.createUser('userId', 'name', 'email')
        def token = tokenManagementApi.createToken(user, ApiTokenType.USER)

        then:
        tokenManagementApi.findUser(token).uuid == user.uuid
    }
}
