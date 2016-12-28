package tech.crom.database.api

import tech.crom.model.project.CromProject
import tech.crom.model.user.CromUser

interface ProjectTrackerManager {

    fun count(cromUser: CromUser): Int
    fun link(cromProject: CromProject, cromUser: CromUser)
    fun unlink(cromProject: CromProject)
}
