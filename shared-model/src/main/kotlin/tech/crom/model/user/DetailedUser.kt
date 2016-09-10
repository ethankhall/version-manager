package tech.crom.model.user

import java.util.*

data class DetailedUser(val uid: UUID, val userName: String, val displayName: String) {
    constructor(user: CromUser): this(user.userUid, user.userName, user.displayName)
}
