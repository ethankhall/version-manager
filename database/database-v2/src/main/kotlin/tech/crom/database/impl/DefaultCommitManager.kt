package tech.crom.database.impl

import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import tech.crom.database.api.CommitManager
import tech.crom.db.Tables
import tech.crom.model.commit.CommitFilter
import tech.crom.model.commit.CommitIdContainer
import tech.crom.model.commit.VersionCommitDetails
import tech.crom.model.commit.VersionDetails
import tech.crom.model.commit.impl.PersistedCommit
import tech.crom.model.commit.impl.RealizedCommit
import tech.crom.model.repository.CromRepo
import tech.crom.model.state.StateTransitionNotification
import tech.crom.toZonedDateTime
import java.time.Clock

@Service
@Transactional
open class DefaultCommitManager @Autowired constructor(
    val dslContext: DSLContext,
    val clock: Clock
) : CommitManager {

    @CacheEvict("allCommitsByRepo", key = "#cromRepo.repoId.toString()")
    override fun createCommit(cromRepo: CromRepo,
                              generatedVersion: RealizedCommit,
                              parent: List<CommitIdContainer>): PersistedCommit {

        val parentCommit = findCommit(cromRepo, CommitFilter(parent))
        val cd = Tables.COMMIT_DETAILS

        val createdAt = generatedVersion.createdAt?.toInstant() ?: clock.instant()
        val record = dslContext
            .insertInto(cd, cd.REPO_DETAIL_ID, cd.PARENT_COMMIT_ID, cd.COMMIT_ID, cd.CREATED_AT, cd.VERSION, cd.STATE)
            .values(cromRepo.repoId, parentCommit?.id, generatedVersion.commitId, createdAt,
                generatedVersion.version.versionString, VersionCommitDetails.DEFAULT_STATE)
            .returning(cd.fields().toList())
            .fetchOne()
            .into(cd)

        return PersistedCommit(record.commitDetailId, record.commitId, VersionDetails(record.version),
            record.state, record.createdAt.toZonedDateTime())
    }

    @Cacheable("allCommitsByRepo", key = "#cromRepo.repoId.toString()")
    override fun findAllCommits(cromRepo: CromRepo): List<PersistedCommit> {
        val cd = Tables.COMMIT_DETAILS

        val commits = dslContext
            .selectFrom(cd)
            .where(cd.REPO_DETAIL_ID.eq(cromRepo.repoId))
            .fetch()
            .into(cd)
        return commits.map {
            PersistedCommit(it.commitDetailId, it.commitId, VersionDetails(it.version), it.state, it.createdAt.toZonedDateTime())
        }
    }

    override fun moveVersionsInState(cromRepo: CromRepo, transitions: List<StateTransitionNotification>) {
        val cd = Tables.COMMIT_DETAILS

        transitions.forEach { (source, target) ->
            //@formatter:off
            dslContext.update(cd)
                .set(cd.STATE, target)
                .where(cd.REPO_DETAIL_ID.eq(cromRepo.repoId))
                    .and(cd.STATE.eq(source))
                .execute()
            //@formatter:on
        }
    }

    override fun setVersionToState(persistedCommit: PersistedCommit, nextState: String) {
        val cd = Tables.COMMIT_DETAILS
        dslContext.update(cd)
            .set(cd.STATE, nextState)
            .where(cd.COMMIT_DETAIL_ID.eq(persistedCommit.id))
            .execute()
    }

    @Cacheable("commitById", key = "#cromRepo.repoId.toString() + #apiCommit.commitId")
    override fun findCommit(cromRepo: CromRepo, apiCommit: CommitIdContainer): PersistedCommit? {
        return findCommit(cromRepo, CommitFilter(listOf(apiCommit)))
    }

    override fun findCommit(cromRepo: CromRepo, filer: CommitFilter): PersistedCommit? {
        val cd = Tables.COMMIT_DETAILS.`as`("cd")

        var query = dslContext
            .selectFrom(cd)
            .where(cd.REPO_DETAIL_ID.eq(cromRepo.repoId))

        if (!filer.history.isEmpty() && !"latest".equals(filer.history[0].commitId, ignoreCase = true)) {
            val commitIds = filer.history.map { it.commitId }
            query = query.and(cd.REPO_DETAIL_ID.eq(cromRepo.repoId)).and(cd.COMMIT_ID.`in`(commitIds).or(cd.VERSION.`in`(commitIds)))
        }

        if (filer.state != null) {
            query = query.and(cd.STATE.eq(filer.state))
        }

        val record = query.orderBy(cd.CREATED_AT.desc()).limit(1).fetchOne() ?: return null

        val details = record.into(cd)
        return PersistedCommit(details.commitDetailId, details.commitId,
            VersionDetails(details.version), details.state, details.createdAt.toZonedDateTime())
    }
}
