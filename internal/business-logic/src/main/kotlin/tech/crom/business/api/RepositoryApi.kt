package tech.crom.business.api

import tech.crom.model.bumper.CromVersionBumper
import tech.crom.model.project.CromProject
import tech.crom.model.repository.CromRepo
import tech.crom.model.repository.CromRepoDetails
import tech.crom.model.state.StateMachineDefinition

interface RepositoryApi {

    fun findRepo(cromProject: CromProject): Collection<CromRepo>

    fun deleteRepo(cromRepo: CromRepo)

    fun createRepo(cromProject: CromProject,
                   repoName: String,
                   versionBumper: CromVersionBumper,
                   checkoutUrl: String?,
                   description: String?,
                   isRepoPublic: Boolean): CromRepo

    fun getRepoDetails(cromRepo: CromRepo): CromRepoDetails

    fun updateVersionStateMachine(cromRepo: CromRepo, stateMachineDefinition: StateMachineDefinition,
                                  inplaceMigrations: Map<String, String>)
}
