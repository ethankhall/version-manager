package tech.crom.model.project

import tech.crom.model.security.authorization.AuthorizedObject
import java.io.Serializable

data class CromProject(val projectId: Long, val securityId: Long, val projectName: String) : AuthorizedObject {
    override fun getId(): Serializable = securityId
}
