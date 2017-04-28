package tech.crom.model.commit

interface VersionCommitDetails : CommitDetails {
    val version: VersionDetails
}
