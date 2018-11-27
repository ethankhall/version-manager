package tech.crom.model.repository

data class CromRepo(val repoId: Long,
                    val securityId: Long,
                    val projectId: Long,
                    val repoName: String,
                    val versionBumperId: Long) {
    override fun toString(): String {
        return repoId.toString()
    }
}
