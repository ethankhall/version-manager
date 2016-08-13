package tech.crom.business.api

import tech.crom.model.bumper.CromVersionBumper
import tech.crom.model.project.CromProject
import tech.crom.model.repository.CromRepo

interface RepositoryApi {

    fun findRepo(cromProject: CromProject): Collection<CromRepo>

    fun deleteRepo(cromRepo: CromRepo)

    fun createRepo(cromProject: CromProject,
                   repoName: String,
                   versionBumper: CromVersionBumper,
                   checkoutUrl: String?,
                   description: String?,
                   isRepoPublic: Boolean): CromRepo
}
