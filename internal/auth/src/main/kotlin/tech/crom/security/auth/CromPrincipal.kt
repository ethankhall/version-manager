package tech.crom.security.auth

import tech.crom.model.security.authorization.CromPermission
import java.security.Principal

interface CromPrincipal: Principal {
    val userName: String
    val permissions: List<CromPermission>

    override fun getName(): String = userName
}

data class ApiUser(override val userName: String, override val permissions: List<CromPermission>): CromPrincipal

class AnonymousUser: CromPrincipal {
    override val userName: String
        get() = "Unknown"
    override val permissions: List<CromPermission>
        get() = emptyList()

}
