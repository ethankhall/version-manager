package io.ehdev.conrad.database.model.user

enum class ApiUserPermission {
    NONE(0),
    READ(1.shl(0)),
    WRITE(1.shl(1)),
    ADMIN(1.shl(2));

    val securityId: Int

    constructor(id: Int) {
        this.securityId = id
    }

    companion object {
        @JvmStatic fun findById(id: Int): ApiUserPermission? {
            for (value in values()) {
                if(value.securityId == id) {
                    return value
                }
            }
            return null;
        }
    }
}
