package tech.crom.business.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tech.crom.business.api.WatcherApi
import tech.crom.database.api.ProjectManager
import tech.crom.database.api.RepoManager
import tech.crom.database.api.WatcherManager
import tech.crom.model.project.CromProject
import tech.crom.model.repository.CromRepo
import tech.crom.model.user.CromUser

@Service
open class DefaultWatcherApi @Autowired constructor(
    val watcherManager: WatcherManager,
    val projectManager: ProjectManager,
    val repoManager: RepoManager
) : WatcherApi {

    override fun addWatch(cromUser: CromUser, cromProject: CromProject) {
        watcherManager.addWatch(cromUser, cromProject)
    }

    override fun addWatch(cromUser: CromUser, cromRepo: CromRepo) {
        watcherManager.addWatch(cromUser, cromRepo)
    }

    override fun deleteWatch(cromUser: CromUser, cromProject: CromProject) {
        watcherManager.deleteWatch(cromUser, cromProject)
    }

    override fun deleteWatch(cromUser: CromUser, cromRepo: CromRepo) {
        watcherManager.deleteWatch(cromUser, cromRepo)
    }

    override fun getWatches(cromUser: CromUser): List<WatcherApi.WatcherDetails> {
        return watcherManager.getWatches(cromUser).map {
            val project = projectManager.findProject(it.projectId)!!
            val repo = if (it.repoId != null) repoManager.findRepo(it.repoId!!) else null
            WatcherApi.WatcherDetails(project, repo)
        }
    }
}
