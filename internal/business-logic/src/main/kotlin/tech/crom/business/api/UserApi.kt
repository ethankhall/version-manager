package tech.crom.business.api

import tech.crom.model.user.CromUser
import tech.crom.model.user.DetailedUser

interface UserApi {

    fun findUserDetails(id: Long): DetailedUser?

    fun findUserDetails(userName: String): DetailedUser?

    fun update(user: CromUser)
}
