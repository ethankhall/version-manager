package tech.crom.business.api

import tech.crom.model.user.CromUser
import tech.crom.model.user.DetailedUser
import java.util.*

interface UserApi {

    fun findUserDetails(uuid: UUID): DetailedUser?

    fun findUserDetails(userName: String): DetailedUser?

    fun update(user: CromUser)
}
