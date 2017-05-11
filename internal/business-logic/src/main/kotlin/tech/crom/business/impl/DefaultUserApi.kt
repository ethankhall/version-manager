package tech.crom.business.impl

import org.springframework.stereotype.Service
import tech.crom.business.api.UserApi
import tech.crom.database.api.UserManager
import tech.crom.model.user.CromUser
import tech.crom.model.user.DetailedUser
import javax.transaction.Transactional

@Service
@Transactional
open class DefaultUserApi(
    val userManager: UserManager
) : UserApi {

    override fun update(user: CromUser) {
        val sourceUser = userManager.findUserDetails(user.userId)!!

        if (user.userName != sourceUser.userName) {
            userManager.changeUserName(sourceUser, user.userName)
        }

        if (user.displayName != sourceUser.displayName) {
            userManager.changeDisplayName(sourceUser, user.displayName)
        }
    }

    override fun findUserDetails(userName: String): DetailedUser? {
        return userManager.findUserDetails(userName)?.toDetailedUser()
    }

    override fun findUserDetails(id: Long): DetailedUser? {
        return userManager.findUserDetails(id)?.toDetailedUser()
    }

    fun CromUser.toDetailedUser(): DetailedUser {
        return DetailedUser(this)
    }
}
