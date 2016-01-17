package io.ehdev.conrad.security.jwt

import io.ehdev.conrad.security.database.model.TokenType
import io.ehdev.conrad.security.database.model.UserModel
import io.ehdev.conrad.security.database.model.UserModelGenerator
import io.ehdev.conrad.security.database.model.UserTokenModel
import io.ehdev.conrad.security.database.repositories.UserTokenModelRepository
import org.springframework.mock.env.MockEnvironment
import spock.lang.Specification

import java.time.LocalDateTime

class JwtManagerImplTest extends Specification {

    JwtManagerImpl jwtManager
    UserTokenModelRepository tokenModelRepository

    def setup() {
        def environment = new MockEnvironment()
        environment.withProperty('jwt.signing.key', 'secret')
        tokenModelRepository = Mock(UserTokenModelRepository)
        jwtManager = new JwtManagerImpl(environment, tokenModelRepository)
    }

    def 'can create token'() {
        UserModel model = UserModelGenerator.createUserModel()
        UserTokenModel tokenModel;

        when:
        def token = jwtManager.createUserToken(model, LocalDateTime.now().plusDays(1))

        then:
        1 * tokenModelRepository.save(_) >> { UserTokenModel it ->
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
