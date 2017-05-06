package tech.crom.model.commit.impl

import tech.crom.model.commit.CommitDetails
import tech.crom.model.commit.VersionCommitDetails
import tech.crom.model.commit.VersionDetails
import java.time.ZonedDateTime

data class PersistedCommit(val id: Long,
                           override val commitId: String,
                           override val version: VersionDetails,
                           override val state: String,
                           val createdAt: ZonedDateTime) : CommitDetails, VersionCommitDetails {

    companion object {
        @JvmStatic
        fun createNewCommit(id: Long, commitId: String, version: String, createdAt: ZonedDateTime): PersistedCommit {
            return PersistedCommit(id, commitId, VersionDetails(version), VersionCommitDetails.DEFAULT_STATE, createdAt)
        }
    }

    fun getVersionString(): String = version.versionString
}
