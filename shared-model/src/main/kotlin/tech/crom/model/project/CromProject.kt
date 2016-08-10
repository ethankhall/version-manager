package tech.crom.model.project

import tech.crom.model.security.authorization.AuthorizedObject
import java.io.Serializable
import java.util.*

data class CromProject(val projectUid: UUID, val securityId: Long, val projectName: String): AuthorizedObject {
    override fun getId(): Serializable = securityId
}
