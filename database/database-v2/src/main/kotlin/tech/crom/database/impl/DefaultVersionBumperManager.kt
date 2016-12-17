package tech.crom.database.impl

import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import tech.crom.database.api.VersionBumperManager
import tech.crom.db.tables.VersionBumpersTable
import tech.crom.model.bumper.CromVersionBumper
import tech.crom.model.repository.CromRepo

@Service
open class DefaultVersionBumperManager @Autowired constructor(
    val dslContext: DSLContext
): VersionBumperManager {

    @Cacheable("versionBumperByName")
    override fun findBumper(name: String): CromVersionBumper? {
        val details = VersionBumpersTable.VERSION_BUMPERS
        val b = dslContext
            .selectFrom(details)
            .where(details.BUMPER_NAME.eq(name))
            .fetchOne()?.into(details) ?: return null
        return CromVersionBumper(b.versionBumperId, b.bumperName, b.className, b.description, createExecutorInstance(b.className))
    }

    @Cacheable("versionBumperByRepo", key = "#cromRepo.repoId.toString()")
    override fun getBumper(cromRepo: CromRepo): CromVersionBumper {
        val details = VersionBumpersTable.VERSION_BUMPERS
        val b = dslContext
            .selectFrom(details)
            .where(details.VERSION_BUMPER_ID.eq(cromRepo.versionBumperId))
            .fetchOne()?.into(details) ?: throw BumperNotFoundException(cromRepo.versionBumperId)
        return CromVersionBumper(b.versionBumperId, b.bumperName, b.className, b.description, createExecutorInstance(b.className))
    }

    override fun findAll(): List<CromVersionBumper> {
        val details = VersionBumpersTable.VERSION_BUMPERS
        return dslContext
            .selectFrom(details)
            .fetch()
            .into(details)
            .map { b -> CromVersionBumper(b.versionBumperId, b.bumperName, b.className, b.description, createExecutorInstance(b.className)) }
    }

    private fun createExecutorInstance(className: String): CromVersionBumper.Executor {
        return Class.forName(className).newInstance() as CromVersionBumper.Executor
    }

    class BumperNotFoundException(bumperId: Long): RuntimeException("Could not find $bumperId.")
}
