package tech.crom.model.repository

import tech.crom.model.security.authorization.AuthorizedObject
import java.io.Serializable
import java.util.*

data class CromRepo(val repoUid: UUID, val securityId: Long, val projectUid: UUID, val repoName: String): AuthorizedObject {
    override fun getId(): Serializable = securityId

    override fun toString(): String {
        return repoUid.toString()
    }
}
