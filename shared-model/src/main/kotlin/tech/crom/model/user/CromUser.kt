package tech.crom.model.user

import java.util.*

data class CromUser(val userUid: UUID, val userName: String, val displayName: String) {
    override fun toString(): String {
        return userUid.toString()
    }

    companion object {
        val REPO_USER = CromUser(UUID.fromString("00000000-0000-0000-0000-000000000000"), "repo-api-user", "repo-api-user")
    }
}
