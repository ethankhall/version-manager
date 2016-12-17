package tech.crom.model.user

data class DetailedUser(val uid: Long, val userName: String, val displayName: String) {
    constructor(user: CromUser): this(user.userId, user.userName, user.displayName)
}
