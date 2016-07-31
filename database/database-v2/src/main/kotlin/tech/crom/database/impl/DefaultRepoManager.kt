package tech.crom.database.impl

import io.ehdev.conrad.db.tables.daos.RepoDetailsDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tech.crom.database.api.RepoManager
import tech.crom.model.repository.CromRepo
import java.util.*

@Service
class DefaultRepoManager @Autowired constructor(
    val repoDetailsDao: RepoDetailsDao
): RepoManager {
    override fun getRepoDetails(uuid: UUID): CromRepo {
        val repo = repoDetailsDao.fetchOneByUuid(uuid)
        return CromRepo(repo.uuid, repo.securityId, repo.repoName)
    }
}
