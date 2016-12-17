package tech.crom.database.impl

import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tech.crom.database.api.WatcherManager
import tech.crom.db.tables.WatcherTable
import tech.crom.model.project.CromProject
import tech.crom.model.repository.CromRepo
import tech.crom.model.user.CromUser

@Service
open class DefaultWatcherManager @Autowired constructor(
    val dslContext: DSLContext
) : WatcherManager {

    override fun addWatch(cromUser: CromUser, cromProject: CromProject) {
        val watcher = WatcherTable.WATCHER

        val size = dslContext.selectCount()
            .from(watcher)
            .where(watcher.USER_ID.eq(cromUser.userId)
                .and(watcher.PROJECT_DETAILS_ID.eq(cromProject.projectId))
                .and(watcher.REPO_DETAILS_ID.isNull))
            .fetchOne(0, Int::class.java)

        if(size == 0) {
            dslContext.insertInto(watcher, watcher.USER_ID, watcher.PROJECT_DETAILS_ID)
                .values(cromUser.userId, cromProject.projectId)
                .execute()
        }
    }

    override fun addWatch(cromUser: CromUser, cromRepo: CromRepo) {
        val watcher = WatcherTable.WATCHER
        val size = dslContext.selectCount()
            .from(watcher)
            .where(watcher.USER_ID.eq(cromUser.userId)
                .and(watcher.PROJECT_DETAILS_ID.eq(cromRepo.projectId))
                .and(watcher.REPO_DETAILS_ID.eq(cromRepo.repoId)))
            .fetchOne(0, Int::class.java)

        if(size == 0) {
            dslContext.insertInto(watcher, watcher.USER_ID, watcher.PROJECT_DETAILS_ID, watcher.REPO_DETAILS_ID)
                .values(cromUser.userId, cromRepo.projectId, cromRepo.repoId)
                .execute()
        }
    }

    override fun deleteWatch(cromUser: CromUser, cromProject: CromProject) {
        val watcher = WatcherTable.WATCHER
        dslContext
            .delete(watcher)
            .where(watcher.USER_ID.eq(cromUser.userId)
                .and(watcher.PROJECT_DETAILS_ID.eq(cromProject.projectId)))
            .execute()
    }

    override fun deleteWatch(cromUser: CromUser, cromRepo: CromRepo) {
        val watcher = WatcherTable.WATCHER
        dslContext
            .delete(watcher)
            .where(watcher.USER_ID.eq(cromUser.userId)
                .and(watcher.PROJECT_DETAILS_ID.eq(cromRepo.projectId))
                .and(watcher.REPO_DETAILS_ID.eq(cromRepo.repoId)))
            .execute()
    }

    override fun getWatches(cromUser: CromUser): List<WatcherManager.UserWatch> {
        val watcher = WatcherTable.WATCHER
        return dslContext
            .selectFrom(watcher)
            .where(watcher.USER_ID.eq(cromUser.userId))
            .fetch()
            .into(watcher)
            .map { WatcherManager.UserWatch(it.projectDetailsId, it.repoDetailsId) }
    }

}
