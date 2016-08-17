package tech.crom.database.api

import tech.crom.model.user.CromUser
import java.util.*

interface UserManager {

    @Throws(UsernameAlreadyExists::class)
    fun createUser(displayName: String, userName: String): CromUser

    @Throws(UsernameAlreadyExists::class)
    fun changeUserName(cromUser: CromUser, newUserName: String): CromUser

    fun findUserDetails(uuid: UUID): CromUser?

    fun findUserDetails(userName: String): CromUser?

    fun userNameExists(userName: String): Boolean

    class UsernameAlreadyExists(newUserName: String): RuntimeException(newUserName)
}
