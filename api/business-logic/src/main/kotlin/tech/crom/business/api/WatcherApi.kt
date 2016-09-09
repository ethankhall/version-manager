package tech.crom.business.api

import tech.crom.model.project.CromProject
import tech.crom.model.repository.CromRepo
import tech.crom.model.user.CromUser

interface WatcherApi {

    fun addWatch(cromUser: CromUser, cromProject: CromProject)

    fun addWatch(cromUser: CromUser, cromRepo: CromRepo)

    fun deleteWatch(cromUser: CromUser, cromProject: CromProject)

    fun deleteWatch(cromUser: CromUser, cromRepo: CromRepo)

    fun getWatches(cromUser: CromUser): List<WatcherDetails>

    data class WatcherDetails(val cromProject: CromProject, val cromRepo: CromRepo?)
}
