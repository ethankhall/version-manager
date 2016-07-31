package io.ehdev.conrad.database.model.user

import io.ehdev.conrad.database.model.permission.UserApiAuthentication
import java.util.*

data class ApiUserDetails(val userId: UUID, val displayName: String, val userName: String) {
    fun toUserApiAuthentication(): UserApiAuthentication {
        return UserApiAuthentication(userId, userName, displayName, "")
    }
}
