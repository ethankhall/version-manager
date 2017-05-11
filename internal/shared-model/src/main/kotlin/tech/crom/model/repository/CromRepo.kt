package tech.crom.model.repository

import tech.crom.model.security.authorization.AuthorizedObject
import java.io.Serializable

data class CromRepo(val repoId: Long,
                    val securityId: Long,
                    val projectId: Long,
                    val repoName: String,
                    val versionBumperId: Long) : AuthorizedObject {
    override fun getId(): Serializable = securityId

    override fun toString(): String {
        return repoId.toString()
    }
}
