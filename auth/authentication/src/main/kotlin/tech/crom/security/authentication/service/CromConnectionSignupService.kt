package tech.crom.security.authentication.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.social.connect.Connection
import org.springframework.social.connect.ConnectionSignUp
import org.springframework.stereotype.Service
import tech.crom.database.api.UserManager
import java.util.*


@Service
class CromConnectionSignupService @Autowired constructor(val userManager: UserManager): ConnectionSignUp {

    internal val randomNumber = Random()

    override fun execute(connection: Connection<*>): String {
        var publicUserName: String

        do {
            val defaultUserId = randomNumber.nextInt(1000000)
            publicUserName = "user$defaultUserId"
        } while (userManager.userNameExists(publicUserName))

        val user = userManager.createUser(publicUserName, connection.fetchUserProfile().name)
        return user.userUid.toString()
    }
}
