package tech.crom.model.commit

interface VersionCommitDetails : CommitDetails {
    val version: VersionDetails
    val state: String

    companion object {
        val DEFAULT_STATE = "__UNDEFINED"
    }
}
