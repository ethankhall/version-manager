package tech.crom.security.authentication.service

import org.springframework.social.connect.Connection
import org.springframework.social.connect.UserProfile
import spock.lang.Specification
import tech.crom.database.api.UserManager
import tech.crom.security.authentication.SharedUtilities

class CromConnectionSignupServiceTest extends Specification {

    def 'will try to create new user'() {
        setup:
        UserManager userManager = Mock(UserManager)
        Connection connection = Mock()
        UserProfile userProfile = Mock(UserProfile)
        def user = SharedUtilities.createUser()

        when:
        def signupService = new CromConnectionSignupService(userManager)

        userProfile.name >> "name"
        3 * userManager.userNameExists(_) >>> [true, true, false]
        connection.fetchUserProfile() >> userProfile
        userManager.createUser(_, _) >> user

        then:

        signupService.execute(connection) == Long.toString(user.userId)
    }
}
