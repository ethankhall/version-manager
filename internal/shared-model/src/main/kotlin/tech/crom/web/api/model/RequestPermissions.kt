package tech.crom.web.api.model

import tech.crom.model.security.authorization.CromPermission
import tech.crom.model.user.CromUser

data class RequestPermissions(val projectPermission: CromPermission?,
                              val repoPermission: CromPermission?,
                              val cromUser: CromUser?) {

    fun findUserName(): String? = cromUser?.userName

    fun findHighestPermission(): CromPermission {
        if (projectPermission == null) {
            return CromPermission.NONE
        }

        if (repoPermission != null) {
            return if (repoPermission.isHigherOrEqualThan(projectPermission)) repoPermission else projectPermission
        }

        return projectPermission
    }
}
