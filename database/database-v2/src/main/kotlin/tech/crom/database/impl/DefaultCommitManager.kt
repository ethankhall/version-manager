package tech.crom.database.impl

import io.ehdev.conrad.db.Tables
import org.jooq.DSLContext
import org.jooq.Record
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import tech.crom.database.api.CommitManager
import tech.crom.model.commit.CommitIdContainer
import tech.crom.model.commit.impl.PersistedCommit
import tech.crom.model.commit.impl.RealizedCommit
import tech.crom.model.repository.CromRepo
import tech.crom.toZonedDateTime
import java.time.Clock

@Service
@Transactional
open class DefaultCommitManager @Autowired constructor(
    val dslContext: DSLContext,
    val clock: Clock
) : CommitManager {

    @CacheEvict("allCommitsByRepo", key="#cromRepo.repoUid.toString()")
    override fun createCommit(cromRepo: CromRepo,
                              generatedVersion: RealizedCommit,
                              parent: List<CommitIdContainer>): PersistedCommit {

        val parentCommit = findLatestCommit(cromRepo, parent)
        val cd = Tables.COMMIT_DETAILS

        val createdAt = generatedVersion.createdAt?.toInstant() ?: clock.instant()
        val record = dslContext
            .insertInto(cd, cd.REPO_DETAILS_UUID, cd.PARENT_COMMIT_UUID, cd.COMMIT_ID, cd.CREATED_AT, cd.VERSION)
            .values(cromRepo.repoUid, parentCommit?.commitUid, generatedVersion.commitId, createdAt, generatedVersion.version.versionString)
            .returning(cd.fields().toList())
            .fetchOne()
            .into(cd)

        return PersistedCommit(record.uuid, record.commitId, record.version, createdAt.toZonedDateTime())
    }

    @Cacheable("commitById", key="#cromRepo.repoUid.toString() + #apiCommit.commitId")
    override fun findCommit(cromRepo: CromRepo, apiCommit: CommitIdContainer): PersistedCommit? {
        val cd = Tables.COMMIT_DETAILS.`as`("cd")

        val query = dslContext
            .selectFrom(cd)
            .where(cd.REPO_DETAILS_UUID.eq(cromRepo.repoUid))

        val record: Record

        if ("latest".equals(apiCommit.commitId, ignoreCase = true)) {
            record = query.orderBy(cd.CREATED_AT.desc()).limit(1).fetchOne()
        } else {
            record = query.and(cd.COMMIT_ID.eq(apiCommit.commitId).or(cd.VERSION.eq(apiCommit.commitId))).fetchOne()
        }

        val detailsRecord = record.into(cd)

        return PersistedCommit(detailsRecord.uuid,
            detailsRecord.commitId,
            detailsRecord.version,
            detailsRecord.createdAt.toZonedDateTime())
    }

    @Cacheable("allCommitsByRepo", key="#cromRepo.repoUid.toString()")
    override fun findAllCommits(cromRepo: CromRepo): List<PersistedCommit> {
        val cd = Tables.COMMIT_DETAILS

        val commits = dslContext
            .selectFrom(cd)
            .where(cd.REPO_DETAILS_UUID.eq(cromRepo.repoUid))
            .fetch()
            .into(cd)
        return commits.map {
            PersistedCommit(it.uuid, it.commitId, it.version, it.createdAt.toZonedDateTime())
        }
    }

    override fun findLatestCommit(cromRepo: CromRepo, history: List<CommitIdContainer>): PersistedCommit? {
        if (!history.isEmpty() && "latest".equals(history[0].commitId, ignoreCase = true)) {
            return findCommit(cromRepo, CommitIdContainer("latest"))
        }

        val commitIds = history.map { it.commitId }.toList()

        val cd = Tables.COMMIT_DETAILS.`as`("cd")
        //@formatter:off
        val record = dslContext
            .selectFrom(cd)
            .where(cd.REPO_DETAILS_UUID.eq(cromRepo.repoUid).and(cd.COMMIT_ID.`in`(commitIds)))
            .orderBy(cd.CREATED_AT.desc())
            .limit(1)
            .fetchOne() ?: return null

        val details = record.into(cd)
        return PersistedCommit(details.uuid, details.commitId, details.version, details.createdAt.toZonedDateTime())
    }
}
