package tech.crom.model.user

import java.util.*

data class CromUser(val userUid: UUID, val userName: String, val displayName: String) {
    override fun toString(): String {
        return userUid.toString()
    }
}
