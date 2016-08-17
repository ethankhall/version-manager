package tech.crom.database.api

import tech.crom.model.bumper.CromVersionBumper
import tech.crom.model.project.CromProject
import tech.crom.model.repository.CromRepo
import java.util.*

interface RepoManager {

    fun findRepo(uuid: UUID): CromRepo?

    fun findRepo(cromProject: CromProject, repoName: String): CromRepo?

    fun findRepo(cromProject: CromProject): Collection<CromRepo>

    fun doesRepoExist(cromProject: CromProject, repoName: String): Boolean

    fun deleteRepo(cromRepo: CromRepo)

    fun createRepo(cromProject: CromProject,
                   repoName: String,
                   versionBumper: CromVersionBumper,
                   checkoutUrl: String?,
                   description: String?,
                   isRepoPublic: Boolean): CromRepo

    fun getDetails(cromRepo: CromRepo): CromRepoDetails

    data class CromRepoDetails(val cromRepo: CromRepo,
                               val cromVersionBumperUid: UUID,
                               val public: Boolean,
                               val checkoutUrl: String?,
                               val description: String?)
}
