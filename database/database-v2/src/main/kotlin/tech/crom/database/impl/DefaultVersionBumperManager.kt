package tech.crom.database.impl

import io.ehdev.conrad.db.tables.daos.VersionBumpersDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tech.crom.database.api.VersionBumperManager
import tech.crom.model.bumper.CromVersionBumper
import tech.crom.model.repository.CromRepo
import java.util.*

@Service
class DefaultVersionBumperManager @Autowired constructor(
    val versionBumpersDao: VersionBumpersDao
): VersionBumperManager {

    override fun findBumper(name: String): CromVersionBumper? {
        val b = versionBumpersDao.fetchOneByBumperName(name) ?: return null
        return CromVersionBumper(b.uuid, b.bumperName, b.className, b.description, createExecutorInstance(b.className))
    }

    override fun getBumper(cromRepo: CromRepo): CromVersionBumper {
        val b = versionBumpersDao.fetchOneByUuid(cromRepo.versionBumperUid) ?: throw BumperNotFoundException(cromRepo.versionBumperUid)
        return CromVersionBumper(b.uuid, b.bumperName, b.className, b.description, createExecutorInstance(b.className))
    }

    private fun createExecutorInstance(className: String): CromVersionBumper.Executor {
        return Class.forName(className).newInstance() as CromVersionBumper.Executor
    }

    class BumperNotFoundException(bumperId: UUID): RuntimeException("Could not find ${bumperId.toString()}.")
}
