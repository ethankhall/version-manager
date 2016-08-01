package tech.crom.database.impl

import io.ehdev.conrad.db.Tables
import org.jooq.DSLContext
import org.jooq.Record
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tech.crom.database.api.CommitManager
import tech.crom.model.commit.CromCommitDetails
import tech.crom.model.repository.CromRepo
import java.time.Clock
import java.time.LocalDateTime
import java.time.ZoneOffset

@Service
class DefaultCommitManager @Autowired constructor(
    val dslContext: DSLContext,
    val clock: Clock
) : CommitManager {

    override fun createCommit(cromRepo: CromRepo,
                              nextVersion: CommitManager.NextCommitVersion,
                              parent: List<CommitManager.CommitSearch>): CromCommitDetails {

        val commit = findLatestCommit(cromRepo, parent)
        val cd = Tables.COMMIT_DETAILS

        val record = dslContext
            .insertInto(cd, cd.REPO_DETAILS_UUID, cd.PARENT_COMMIT_UUID, cd.COMMIT_ID, cd.CREATED_AT, cd.VERSION)
            .values(cromRepo.repoUid, commit?.commitUid, nextVersion.commitId, nextVersion.createdAt ?: clock.instant(), nextVersion.version)
            .returning(cd.fields().toList())
            .fetchOne()
            .into(cd)

        return CromCommitDetails(record.uuid, record.commitId, record.version, LocalDateTime.ofInstant(record.createdAt, ZoneOffset.UTC))
    }

    override fun findCommit(cromRepo: CromRepo, apiCommit: CommitManager.CommitSearch): CromCommitDetails? {
        val cd = Tables.COMMIT_DETAILS.`as`("cd")

        val query = dslContext
            .select()
            .from(cd)
            .where(cd.REPO_DETAILS_UUID.eq(cromRepo.repoUid))

        val record: Record

        if ("latest".equals(apiCommit.commitId, ignoreCase = true)) {
            record = query.orderBy(cd.CREATED_AT.desc()).limit(1).fetchOne()
        } else {
            record = query.and(cd.COMMIT_ID.eq(apiCommit.commitId).or(cd.VERSION.eq(apiCommit.commitId))).fetchOne()
        }

        val detailsRecord = record.into(cd)
        val createdAt = LocalDateTime.ofInstant(detailsRecord.createdAt, ZoneOffset.UTC)

        return CromCommitDetails(detailsRecord.uuid, detailsRecord.commitId, detailsRecord.version, createdAt)
    }

    override fun findAllCommits(cromRepo: CromRepo): List<CromCommitDetails> {
        val cd = Tables.COMMIT_DETAILS

        val commits = dslContext
            .select(cd.fields().toList())
            .from(cd)
            .where(cd.REPO_DETAILS_UUID.eq(cromRepo.repoUid))
            .fetch()
            .into(cd)
        return commits.map {
            CromCommitDetails(it.uuid, it.commitId, it.version, LocalDateTime.ofInstant(it.createdAt, ZoneOffset.UTC))
        }
    }

    override fun findLatestCommit(cromRepo: CromRepo, history: List<CommitManager.CommitSearch>): CromCommitDetails? {
        if (!history.isEmpty() && "latest".equals(history[0].commitId, ignoreCase = true)) {
            return findCommit(cromRepo, CommitManager.CommitSearch("latest"))
        }

        val commitIds = history.map { it.commitId }.toList()

        val cd = Tables.COMMIT_DETAILS.`as`("cd")
        //@formatter:off
        val record = dslContext
            .select()
            .from(cd)
            .where(cd.REPO_DETAILS_UUID.eq(cromRepo.repoUid).and(cd.COMMIT_ID.`in`(commitIds)))
            .orderBy(cd.CREATED_AT.desc())
            .limit(1)
            .fetchOne() ?: return null

        val details = record.into(cd)
        val createdAt = LocalDateTime.ofInstant(details.createdAt, ZoneOffset.UTC)
        return CromCommitDetails(details.uuid, details.commitId, details.version, createdAt)
    }
}
