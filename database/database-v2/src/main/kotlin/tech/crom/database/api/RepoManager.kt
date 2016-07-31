package tech.crom.database.api

import tech.crom.model.repository.CromRepo
import java.util.*

interface RepoManager {
    fun getRepoDetails(uuid: UUID): CromRepo
}
