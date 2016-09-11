package tech.crom.database.impl

import io.ehdev.conrad.db.Tables
import io.ehdev.conrad.db.tables.daos.RepoDetailsDao
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
import org.springframework.stereotype.Service
import tech.crom.database.api.RepoManager
import tech.crom.model.bumper.CromVersionBumper
import tech.crom.model.project.CromProject
import tech.crom.model.repository.CromRepo
import java.util.*

@Service
open class DefaultRepoManager @Autowired constructor(
    val dslContext: DSLContext,
    val repoDetailsDao: RepoDetailsDao
) : RepoManager {

    override fun doesRepoExist(cromProject: CromProject, repoName: String): Boolean {
        val repoDetails = Tables.REPO_DETAILS
        return dslContext
            .selectCount()
            .from(repoDetails)
            .where(repoDetails.PROJECT_UUID.eq(cromProject.projectUid)).and(repoDetails.REPO_NAME.eq(repoName))
            .fetchOne(0, Int::class.java) == 1
    }

    @Caching(evict = arrayOf(
        CacheEvict("repoDetailsByUid", key="#cromRepo.repoUid"),
        CacheEvict("repoByProjectAndName", key="#cromRepo.projectUid.toString() + #cromRepo.repoName")))
    override fun deleteRepo(cromRepo: CromRepo) {
        val tokens = Tables.REPOSITORY_TOKENS
        dslContext
            .deleteFrom(tokens)
            .where(tokens.REPO_UUID.eq(cromRepo.repoUid))
            .execute()

        val repoDetails = Tables.REPO_DETAILS
        dslContext
            .deleteFrom(repoDetails)
            .where(repoDetails.UUID.eq(cromRepo.repoUid))
            .execute()
    }

    @Caching(evict = arrayOf(
        CacheEvict("repoDetailsByUid", allEntries = true),
        CacheEvict("repoByProjectAndName", key="#cromProject.projectUid.toString() + #repoName")))
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

        return CromRepo(record.uuid, record.securityId, record.projectUuid, record.repoName, record.versionBumperUuid)
    }

    @Cacheable("repoByProjectAndName", key = "#cromProject.projectUid.toString() + #repoName")
    override fun findRepo(cromProject: CromProject, repoName: String): CromRepo? {
        val repoDetails = Tables.REPO_DETAILS
        val record = dslContext
            .select(repoDetails.fields().toList())
            .from(repoDetails)
            .where(repoDetails.REPO_NAME.eq(repoName)).and(repoDetails.PROJECT_UUID.eq(cromProject.projectUid))
            .fetchOne()
            ?.into(repoDetails) ?: return null

        return CromRepo(record.uuid, record.securityId, record.projectUuid, record.repoName, record.versionBumperUuid)
    }

    override fun findRepo(cromProject: CromProject): Collection<CromRepo> {
        val details = Tables.REPO_DETAILS
        return dslContext
            .select(details.fields().toList())
            .from(details)
            .where(details.PROJECT_UUID.eq(cromProject.projectUid))
            .fetch()
            .into(details)
            .map { CromRepo(it.uuid, it.securityId, it.projectUuid, it.repoName, it.versionBumperUuid) }
    }

    @Cacheable("repoByUid")
    override fun findRepo(uuid: UUID): CromRepo? {
        val repo = repoDetailsDao.fetchOneByUuid(uuid) ?: return null
        return CromRepo(repo.uuid, repo.securityId, repo.projectUuid, repo.repoName, repo.versionBumperUuid)
    }

    @Cacheable("repoDetailsByUid", key="#cromRepo.repoUid")
    override fun getDetails(cromRepo: CromRepo): RepoManager.CromRepoDetails {
        val repo = repoDetailsDao.fetchOneByUuid(cromRepo.repoUid)
        return RepoManager.CromRepoDetails(cromRepo, repo.versionBumperUuid, repo.public, repo.url, repo.description)
    }
}
