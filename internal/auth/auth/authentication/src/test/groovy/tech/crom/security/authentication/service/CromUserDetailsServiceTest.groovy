package tech.crom.security.authentication.service

import org.springframework.security.core.userdetails.UsernameNotFoundException
import spock.lang.Specification
import tech.crom.database.api.UserManager

import static tech.crom.security.authentication.SharedUtilities.createUser
import static tech.crom.security.authentication.SharedUtilities.randomNumber

class CromUserDetailsServiceTest extends Specification {

    def 'will throw when userid is bad'() {
        when:
        UserManager userManager = Mock(UserManager)
        def uds = new CromUserDetailsService(userManager)
        1 * userManager.findUserDetails(_ as Long) >> null

        uds.loadUserByUserId(Long.toString(randomNumber()))

        then:
        thrown(UsernameNotFoundException)

        when:
        1 * userManager.findUserDetails(_ as String) >> null

        uds.loadUserByUsername("")

        then:
        thrown(UsernameNotFoundException)
    }

    def 'will respond when asked for user by loadUserByUserId'() {
        when:
        def cromUser = createUser()
        UserManager userManager = Mock(UserManager)
        1 * userManager.findUserDetails(_ as String) >> cromUser

        def uds = new CromUserDetailsService(userManager)

        then:
        def loadedUser = uds.loadUserByUsername(Long.toString(cromUser.userId))
        assert loadedUser
        cromUser.userId as String == loadedUser.userId
    }

    def 'will respond when asked for user by loadUserByUsername'() {
        when:
        def cromUser = createUser()
        UserManager userManager = Mock(UserManager)
        1 * userManager.findUserDetails(_ as String) >> cromUser

        def uds = new CromUserDetailsService(userManager)

        then:
        def loadedUser = uds.loadUserByUsername(Long.toString(cromUser.userId))
        assert loadedUser
        cromUser.userName == loadedUser.username
    }
}
