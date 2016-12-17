package tech.crom.database.impl

import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
import org.springframework.stereotype.Service
import tech.crom.database.api.RepoManager
import tech.crom.db.Tables
import tech.crom.db.tables.RepoDetailsTable
import tech.crom.db.tables.records.RepoDetailsRecord
import tech.crom.model.bumper.CromVersionBumper
import tech.crom.model.project.CromProject
import tech.crom.model.repository.CromRepo

@Service
open class DefaultRepoManager @Autowired constructor(
    val dslContext: DSLContext
) : RepoManager {

    override fun doesRepoExist(cromProject: CromProject, repoName: String): Boolean {
        val repoDetails = Tables.REPO_DETAILS
        return dslContext
            .selectCount()
            .from(repoDetails)
            .where(repoDetails.PROJECT_ID.eq(cromProject.projectId)).and(repoDetails.REPO_NAME.eq(repoName))
            .fetchOne(0, Int::class.java) == 1
    }

    @Caching(evict = arrayOf(
        CacheEvict("repoByUid", key="#cromRepo.repoId"),
        CacheEvict("repoDetailsByUid", key="#cromRepo.repoId"),
        CacheEvict("repoByProjectAndName", key="#cromRepo.projectId.toString() + #cromRepo.repoName")))
    override fun deleteRepo(cromRepo: CromRepo) {
        val tokens = Tables.REPOSITORY_TOKENS
        dslContext
            .deleteFrom(tokens)
            .where(tokens.REPO_ID.eq(cromRepo.repoId))
            .execute()

        val repoDetails = Tables.REPO_DETAILS
        dslContext
            .deleteFrom(repoDetails)
            .where(repoDetails.REPO_DETAILS_ID.eq(cromRepo.repoId))
            .execute()
    }

    @Caching(evict = arrayOf(
        CacheEvict("repoByUid", allEntries = true),
        CacheEvict("repoDetailsByUid", allEntries = true),
        CacheEvict("repoByProjectAndName", key="#cromProject.projectId.toString() + #repoName")))
    override fun createRepo(cromProject: CromProject,
                            repoName: String,
                            versionBumper: CromVersionBumper,
                            checkoutUrl: String?,
                            description: String?,
                            isRepoPublic: Boolean): CromRepo {

        val repoDetails = Tables.REPO_DETAILS
        val record = dslContext.insertInto(repoDetails,
            repoDetails.PROJECT_ID,
            repoDetails.REPO_NAME,
            repoDetails.VERSION_BUMPER_ID,
            repoDetails.URL,
            repoDetails.DESCRIPTION,
            repoDetails.PUBLIC)
            .values(cromProject.projectId, repoName, versionBumper.bumperId, checkoutUrl, description, isRepoPublic)
            .returning(repoDetails.fields().toList())
            .fetchOne()
            .into(repoDetails)

        return CromRepo(record.repoDetailsId, record.securityId, record.projectId, record.repoName, record.versionBumperId)
    }

    @Cacheable("repoByProjectAndName", key = "#cromProject.projectId.toString() + #repoName")
    override fun findRepo(cromProject: CromProject, repoName: String): CromRepo? {
        val repoDetails = Tables.REPO_DETAILS
        val record = dslContext
            .selectFrom(repoDetails)
            .where(repoDetails.REPO_NAME.eq(repoName)).and(repoDetails.PROJECT_ID.eq(cromProject.projectId))
            .fetchOne()
            ?.into(repoDetails) ?: return null

        return CromRepo(record.repoDetailsId, record.securityId, record.projectId, record.repoName, record.versionBumperId)
    }

    override fun findRepo(cromProject: CromProject): Collection<CromRepo> {
        val details = Tables.REPO_DETAILS
        return dslContext
            .selectFrom(details)
            .where(details.PROJECT_ID.eq(cromProject.projectId))
            .fetch()
            .into(details)
            .map { CromRepo(it.repoDetailsId, it.securityId, it.projectId, it.repoName, it.versionBumperId) }
    }

    @Cacheable("repoByUid")
    override fun findRepo(id: Long): CromRepo? {
        val repo = fetchOneRepoDetailsByUid(id) ?: return null
        return CromRepo(repo.repoDetailsId, repo.securityId, repo.projectId, repo.repoName, repo.versionBumperId)
    }

    @Cacheable("repoDetailsByUid", key="#cromRepo.repoId")
    override fun getDetails(cromRepo: CromRepo): RepoManager.CromRepoDetails {
        val repo = fetchOneRepoDetailsByUid(cromRepo.repoId)!!
        return RepoManager.CromRepoDetails(cromRepo, repo.versionBumperId, repo.public, repo.url, repo.description)
    }

    private fun fetchOneRepoDetailsByUid(id: Long): RepoDetailsRecord? {
        val details = RepoDetailsTable.REPO_DETAILS
        return dslContext
            .selectFrom(details)
            .where(details.REPO_DETAILS_ID.eq(id))
            .fetchOne()?.into(details)
    }
}
