package tech.crom.database.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification
import tech.crom.config.ClockConfig
import tech.crom.config.DatabaseConfig
import tech.crom.database.api.UserManager
import tech.crom.database.config.CromDoaConfig

@Transactional
@SpringBootTest(classes = [DatabaseConfig, ClockConfig, CromDoaConfig], webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserManagerIntegrationTest extends Specification {

    @Autowired
    UserManager userManager

    def 'can add user'() {
        expect:
        !userManager.userNameExists('userName')

        when:
        def user = userManager.createUser('displayName', 'userName')

        then:
        userManager.userNameExists('userName')
        userManager.findUserDetails('userName') == user
        userManager.findUserDetails(user.userId) == user

        when:
        userManager.createUser('displayName1', 'userName')

        then:
        thrown(UserManager.UsernameAlreadyExists)

        when:
        userManager.createUser('displayName', 'userName1')

        then:
        noExceptionThrown()

        when:
        userManager.changeUserName(user, 'userName1')

        then:
        thrown(UserManager.UsernameAlreadyExists)

        when:
        def newUserName = userManager.changeUserName(user, 'newUserName')

        then:
        noExceptionThrown()
        newUserName.userName == 'newUserName'
        newUserName.userId == user.userId
    }
}
