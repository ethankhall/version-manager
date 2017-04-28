package tech.crom.model.user

data class DetailedUser(val id: Long, val userName: String, val displayName: String) {
    constructor(user: CromUser) : this(user.userId, user.userName, user.displayName)
}
