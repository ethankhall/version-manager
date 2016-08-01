package tech.crom.database.impl

import io.ehdev.conrad.db.tables.daos.VersionBumpersDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tech.crom.database.api.VersionBumperManager
import tech.crom.model.bumper.CromVersionBumper

@Service
class DefaultVersionBumperManager @Autowired constructor(
    val versionBumpersDao: VersionBumpersDao
): VersionBumperManager {

    override fun findBumper(name: String): CromVersionBumper? {
        val bumper = versionBumpersDao.fetchOneByBumperName(name) ?: return null
        return CromVersionBumper(bumper.uuid, bumper.bumperName, bumper.className, bumper.description)
    }

}
