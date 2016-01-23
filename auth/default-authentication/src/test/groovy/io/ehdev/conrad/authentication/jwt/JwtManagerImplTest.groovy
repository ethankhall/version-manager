package io.ehdev.conrad.authentication.jwt

import io.ehdev.conrad.authentication.database.model.TokenType
import io.ehdev.conrad.authentication.database.model.SecurityUserModel
import io.ehdev.conrad.authentication.database.model.UserModelGenerator
import io.ehdev.conrad.authentication.database.model.SecurityUserTokenModel
import io.ehdev.conrad.authentication.database.repositories.SecurityUserTokenModelRepository
import org.springframework.mock.env.MockEnvironment
import spock.lang.Specification

import java.time.LocalDateTime

class JwtManagerImplTest extends Specification {

    JwtManagerImpl jwtManager
    SecurityUserTokenModelRepository tokenModelRepository

    def setup() {
        def environment = new MockEnvironment()
        environment.withProperty('jwt.signing.key', 'secret')
        tokenModelRepository = Mock(SecurityUserTokenModelRepository)
        jwtManager = new JwtManagerImpl(environment, tokenModelRepository)
    }

    def 'can create token'() {
        SecurityUserModel model = UserModelGenerator.createUserModel()
        SecurityUserTokenModel tokenModel;

        when:
        def token = jwtManager.createUserToken(model, LocalDateTime.now().plusDays(1))

        then:
        1 * tokenModelRepository.save(_) >> { SecurityUserTokenModel it ->
            it.id = UUID.randomUUID()
            tokenModel = it
            return it;
        }

        when:
        def parsedToken = jwtManager.parseToken(token)

        then:
        parsedToken.userId == model.id.toString()
        parsedToken.getUniqueId() == tokenModel.id.toString()
        parsedToken.type == TokenType.USER
        1 * tokenModelRepository.findOne(_) >> tokenModel
    }

    def 'returns null on invalid token'() {
        expect:
        jwtManager.parseToken('abcdef') == null
    }

}
