package tech.crom.database.impl

import io.ehdev.conrad.db.tables.WatcherTable
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tech.crom.database.api.WatcherManager
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
            .where(watcher.USER_UUID.eq(cromUser.userUid)
                .and(watcher.PROJECT_DETAILS_UUID.eq(cromProject.projectUid))
                .and(watcher.REPO_DETAILS_UUID.isNull))
            .fetchOne(0, Int::class.java)

        if(size == 0) {
            dslContext.insertInto(watcher, watcher.USER_UUID, watcher.PROJECT_DETAILS_UUID)
                .values(cromUser.userUid, cromProject.projectUid)
                .execute()
        }
    }

    override fun addWatch(cromUser: CromUser, cromRepo: CromRepo) {
        val watcher = WatcherTable.WATCHER
        val size = dslContext.selectCount()
            .from(watcher)
            .where(watcher.USER_UUID.eq(cromUser.userUid)
                .and(watcher.PROJECT_DETAILS_UUID.eq(cromRepo.projectUid))
                .and(watcher.REPO_DETAILS_UUID.eq(cromRepo.repoUid)))
            .fetchOne(0, Int::class.java)

        if(size == 0) {
            dslContext.insertInto(watcher, watcher.USER_UUID, watcher.PROJECT_DETAILS_UUID, watcher.REPO_DETAILS_UUID)
                .values(cromUser.userUid, cromRepo.projectUid, cromRepo.repoUid)
                .execute()
        }
    }

    override fun deleteWatch(cromUser: CromUser, cromProject: CromProject) {
        val watcher = WatcherTable.WATCHER
        dslContext
            .delete(watcher)
            .where(watcher.USER_UUID.eq(cromUser.userUid)
                .and(watcher.PROJECT_DETAILS_UUID.eq(cromProject.projectUid)))
            .execute()
    }

    override fun deleteWatch(cromUser: CromUser, cromRepo: CromRepo) {
        val watcher = WatcherTable.WATCHER
        dslContext
            .delete(watcher)
            .where(watcher.USER_UUID.eq(cromUser.userUid)
                .and(watcher.PROJECT_DETAILS_UUID.eq(cromRepo.projectUid))
                .and(watcher.REPO_DETAILS_UUID.eq(cromRepo.repoUid)))
            .execute()
    }

    override fun getWatches(cromUser: CromUser): List<WatcherManager.UserWatch> {
        val watcher = WatcherTable.WATCHER
        return dslContext
            .selectFrom(watcher)
            .where(watcher.USER_UUID.eq(cromUser.userUid))
            .fetch()
            .into(watcher)
            .map { WatcherManager.UserWatch(it.projectDetailsUuid, it.repoDetailsUuid) }
    }

}
