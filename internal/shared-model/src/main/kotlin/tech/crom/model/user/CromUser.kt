package tech.crom.model.user

data class CromUser(val userId: Long, val userName: String, val displayName: String) {
    override fun toString(): String {
        return userId.toString()
    }

    companion object {
        val REPO_USER = CromUser(-1, "repo-api-user", "repo-api-user")
    }
}
