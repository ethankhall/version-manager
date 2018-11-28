package tech.crom.model.security.authorization

enum class CromPermission(val permissionLevel: Int) {
    NONE(0),
    READ(1),
    WRITE(2),
    ADMIN(3);

    fun isHigherOrEqualThan(permission: CromPermission) = permissionLevel >= permission.permissionLevel
}
