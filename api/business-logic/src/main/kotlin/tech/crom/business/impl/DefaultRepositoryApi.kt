package tech.crom.business.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tech.crom.business.api.RepositoryApi
import tech.crom.database.api.RepoManager
import tech.crom.model.bumper.CromVersionBumper
import tech.crom.model.project.CromProject
import tech.crom.model.repository.CromRepo
import tech.crom.security.authorization.api.PermissionService

@Service
class DefaultRepositoryApi @Autowired constructor(
    val repoManager: RepoManager,
    val permissionService: PermissionService
): RepositoryApi {

    override fun deleteRepo(cromRepo: CromRepo) {
        repoManager.deleteRepo(cromRepo)
        permissionService.remove(cromRepo)
    }

    override fun createRepo(cromProject: CromProject,
                            repoName: String,
                            versionBumper: CromVersionBumper,
                            checkoutUrl: String?,
                            description: String?,
                            isRepoPublic: Boolean): CromRepo {

        val repo = repoManager.createRepo(cromProject, repoName, versionBumper, checkoutUrl, description, isRepoPublic)
        permissionService.registerRepository(repo)
        return repo
    }

    override fun findRepo(cromProject: CromProject): Collection<CromRepo> = repoManager.findRepo(cromProject)

}
