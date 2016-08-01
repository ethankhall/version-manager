package tech.crom.database.impl

import io.ehdev.conrad.db.Tables
import io.ehdev.conrad.db.tables.daos.RepoDetailsDao
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tech.crom.database.api.RepoManager
import tech.crom.model.bumper.CromVersionBumper
import tech.crom.model.project.CromProject
import tech.crom.model.repository.CromRepo
import java.util.*

@Service
class DefaultRepoManager @Autowired constructor(
    val dslContext: DSLContext,
    val repoDetailsDao: RepoDetailsDao
) : RepoManager {

    override fun doesRepoExist(cromProject: CromProject, repoName: String): Boolean {
        val repoDetails = Tables.REPO_DETAILS
        return dslContext
            .selectCount()
            .from(repoDetails)
            .where(repoDetails.PROJECT_UUID.eq(cromProject.projectUid)).and(repoDetails.REPO_NAME.eq(repoName))
            .execute() == 1
    }

    override fun deleteRepo(cromRepo: CromRepo) = repoDetailsDao.deleteById(cromRepo.repoUid)

    override fun createRepo(cromProject: CromProject,
                            repoName: String,
                            versionBumper: CromVersionBumper,
                            checkoutUrl: String?,
                            description: String?,
                            isRepoPublic: Boolean): CromRepo {

        val repoDetails = Tables.REPO_DETAILS
        val record = dslContext.insertInto(repoDetails,
            repoDetails.PROJECT_UUID,
            repoDetails.REPO_NAME,
            repoDetails.VERSION_BUMPER_UUID,
            repoDetails.URL,
            repoDetails.DESCRIPTION,
            repoDetails.PUBLIC)
            .values(cromProject.projectUid, repoName, versionBumper.bumperUuid, checkoutUrl, description, isRepoPublic)
            .returning(repoDetails.fields().toList())
            .fetchOne()
            .into(repoDetails)

        return CromRepo(record.uuid, record.securityId, record.projectUuid, record.repoName)
    }

    override fun findRepo(cromProject: CromProject, repoName: String): CromRepo? {
        val repoDetails = Tables.REPO_DETAILS
        val detailLookup = dslContext
            .select(repoDetails.fields().toList())
            .from(repoDetails)
            .where(repoDetails.REPO_NAME.eq(repoName)).and(repoDetails.PROJECT_UUID.eq(cromProject.projectUid))
            .fetchOne()
            ?.into(repoDetails) ?: return null

        return CromRepo(detailLookup.uuid, detailLookup.securityId, detailLookup.projectUuid, detailLookup.repoName)
    }

    override fun findRepo(cromProject: CromProject): Collection<CromRepo> {
        return repoDetailsDao.fetchByProjectUuid(cromProject.projectUid).map {
            CromRepo(it.uuid, it.securityId, it.projectUuid, it.repoName)
        }
    }

    override fun findRepo(uuid: UUID): CromRepo? {
        val repo = repoDetailsDao.fetchOneByUuid(uuid) ?: return null
        return CromRepo(repo.uuid, repo.securityId, repo.projectUuid, repo.repoName)
    }
}
