package tech.crom.database.api

import tech.crom.model.project.CromProject
import tech.crom.model.repository.CromRepo
import tech.crom.model.user.CromUser
import java.util.*

interface WatcherManager {

    fun addWatch(cromUser: CromUser, cromProject: CromProject)

    fun addWatch(cromUser: CromUser, cromRepo: CromRepo)

    fun deleteWatch(cromUser: CromUser, cromProject: CromProject)

    fun deleteWatch(cromUser: CromUser, cromRepo: CromRepo)

    fun getWatches(cromUser: CromUser): List<UserWatch>

    data class UserWatch(val projectUuid: UUID, val repoUuid: UUID?)
}
