package tech.crom.database.api

import tech.crom.model.user.CromUser

interface UserManager {

    @Throws(UsernameAlreadyExists::class)
    fun createUser(displayName: String, userName: String): CromUser

    @Throws(UsernameAlreadyExists::class)
    fun changeUserName(cromUser: CromUser, newUserName: String): CromUser

    fun findUserDetails(id: Long): CromUser?

    fun findUserDetails(userName: String): CromUser?

    fun userNameExists(userName: String): Boolean

    fun changeDisplayName(sourceUser: CromUser, displayName: String)

    class UsernameAlreadyExists(newUserName: String) : RuntimeException(newUserName)
}
