package tech.crom.business.impl

import org.springframework.stereotype.Service
import tech.crom.business.api.UserApi
import tech.crom.database.api.UserManager
import tech.crom.model.user.CromUser
import tech.crom.model.user.DetailedUser
import java.util.*
import javax.transaction.Transactional

@Service
@Transactional
open class DefaultUserApi(
    val userManager: UserManager
): UserApi{
    override fun findUserDetails(userName: String): DetailedUser? {
        return userManager.findUserDetails(userName)?.toDetailedUser()
    }

    override fun findUserDetails(uuid: UUID): DetailedUser? {
        return userManager.findUserDetails(uuid)?.toDetailedUser()
    }

    fun CromUser.toDetailedUser(): DetailedUser {
        return DetailedUser(this.userName, this.displayName)
    }

}
