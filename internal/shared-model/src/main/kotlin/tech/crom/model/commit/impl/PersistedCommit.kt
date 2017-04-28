package tech.crom.model.commit.impl

import tech.crom.model.commit.CommitDetails
import tech.crom.model.commit.VersionCommitDetails
import tech.crom.model.commit.VersionDetails
import java.time.ZonedDateTime

data class PersistedCommit(val id: Long,
                           override val commitId: String,
                           override val version: VersionDetails,
                           val createdAt: ZonedDateTime) : CommitDetails, VersionCommitDetails {
    constructor(id: Long,
                commitId: String,
                version: String,
                createdAt: ZonedDateTime) : this(id, commitId, VersionDetails(version), createdAt)

    fun getVersionString(): String = version.versionString
}
