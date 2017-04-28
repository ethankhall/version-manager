package tech.crom.database.impl

import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.stereotype.Service
import tech.crom.database.api.MetaDataManager
import tech.crom.db.Tables
import tech.crom.model.commit.impl.PersistedCommit
import tech.crom.model.metadata.MetaDataModel
import tech.crom.model.metadata.StorageData
import tech.crom.model.project.CromProject
import tech.crom.model.repository.CromRepo
import java.net.URI
import java.time.ZonedDateTime

@Service
open class DefaultMetaDataManager(
    val dslContext: DSLContext
) : MetaDataManager {

    val mdt = Tables.COMMIT_METADATA

    override fun getCurrentSizeOfProject(cromRepo: CromRepo): Long {
        return getSizeOfProject(cromRepo.projectId)
    }

    private fun getSizeOfProject(id: Long): Long {
        return dslContext
            .select(DSL.sum(mdt.SIZE))
            .from(mdt)
            .where(mdt.PROJECT_ID.eq(id))
            .fetchOne()?.value1()?.toLong() ?: return 0
    }

    override fun insertFile(cromRepo: CromRepo, persistedCommit: PersistedCommit, uri: URI, storageData: StorageData) {
        val id: Long? = dslContext
            .select(mdt.COMMIT_METADATA_ID)
            .from(mdt)
            .where(
                mdt.COMMIT_ID.eq(persistedCommit.id)
                    .and(mdt.NAME.eq(storageData.fileName)))
            .fetchOne()?.value1()

        if (id != null) {
            dslContext
                .update(mdt)
                .set(mdt.SIZE, storageData.bytes.size.toLong())
                .set(mdt.CONTENT_TYPE, storageData.contentType)
                .where(mdt.COMMIT_METADATA_ID.eq(id))
                .execute()
        } else {
            dslContext
                .insertInto(mdt, mdt.PROJECT_ID, mdt.REPO_ID, mdt.COMMIT_ID, mdt.NAME, mdt.URI, mdt.SIZE, mdt.CONTENT_TYPE, mdt.UPDATED_AT)
                .values(
                    cromRepo.projectId,
                    cromRepo.repoId,
                    persistedCommit.id,
                    storageData.fileName,
                    uri.toString(),
                    storageData.bytes.size.toLong(),
                    storageData.contentType,
                    ZonedDateTime.now().toInstant())
                .execute()
        }
    }

    override fun findFile(version: PersistedCommit, fileName: String): MetaDataModel? {
        val data = dslContext
            .select(mdt.URI, mdt.NAME, mdt.CONTENT_TYPE)
            .from(mdt)
            .where(mdt.COMMIT_ID.eq(version.id).and(mdt.NAME.eq(fileName)))
            .fetchOne()?.into(mdt) ?: return null

        return MetaDataModel(data.name, URI.create(data.uri), data.contentType)
    }

    override fun listFiles(version: PersistedCommit): List<String> {
        return dslContext
            .select(mdt.NAME)
            .from(mdt)
            .where(mdt.COMMIT_ID.eq(version.id))
            .fetch()
            .into(String::class.java)
    }

    override fun getCurrentSizeOfProject(cromProject: CromProject): Long {
        return getSizeOfProject(cromProject.projectId)
    }
}
