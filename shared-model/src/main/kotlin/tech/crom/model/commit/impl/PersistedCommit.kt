package tech.crom.model.commit.impl

import tech.crom.model.commit.CommitDetails
import tech.crom.model.commit.VersionCommitDetails
import tech.crom.model.commit.VersionDetails
import java.time.ZonedDateTime
import java.util.*

data class PersistedCommit(val commitUid: UUID,
                           override val commitId: String,
                           override val version: VersionDetails,
                           val createdAt: ZonedDateTime) : CommitDetails, VersionCommitDetails {
    constructor(commitUid: UUID,
                commitId: String,
                version: String,
                createdAt: ZonedDateTime): this(commitUid, commitId, VersionDetails(version), createdAt)

    fun getVersionString(): String = version.versionString
}
