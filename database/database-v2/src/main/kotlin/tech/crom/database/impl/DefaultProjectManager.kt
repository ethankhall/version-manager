package tech.crom.database.impl

import io.ehdev.conrad.db.Tables
import io.ehdev.conrad.db.tables.ProjectDetailsTable
import io.ehdev.conrad.db.tables.daos.ProjectDetailsDao
import io.ehdev.conrad.db.tables.pojos.ProjectDetails
import io.ehdev.conrad.db.tables.records.ProjectDetailsRecord
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
import org.springframework.stereotype.Service
import tech.crom.database.api.ProjectManager
import tech.crom.model.project.CromProject
import tech.crom.model.project.FilteredProjects
import java.util.*

@Service
open class DefaultProjectManager @Autowired constructor(
    val dslContext: DSLContext,
    val projectDetailsDao: ProjectDetailsDao
) : ProjectManager {

    @Caching(evict = arrayOf(
        CacheEvict("projectByName", key="#project.projectName"),
        CacheEvict("projectById", key="#project.projectUid.toString()")
    ))
    override fun deleteProject(project: CromProject) {
        val watcher = Tables.WATCHER
        dslContext
            .deleteFrom(watcher)
            .where(watcher.PROJECT_DETAILS_UUID.eq(project.projectUid))
            .execute()

        val details = Tables.PROJECT_DETAILS
        dslContext
            .deleteFrom(details)
            .where(details.UUID.eq(project.projectUid))
            .execute()
    }

    override fun findProjects(offset: Int, size: Int): FilteredProjects {
        val filteredProjects = dslContext.select()
            .from(ProjectDetailsTable.PROJECT_DETAILS)
            .offset(offset)
            .limit(size)
            .fetch()
            .into(ProjectDetailsTable.PROJECT_DETAILS)
            .map { it.toCromProject() }

        val tableSize = dslContext.selectCount()
            .from(ProjectDetailsTable.PROJECT_DETAILS)
            .fetchOne(0, Int::class.java)

        return FilteredProjects(filteredProjects, tableSize)
    }

    @Cacheable("projectByName")
    override fun findProject(projectName: String): CromProject? {
        val projectDetails = projectDetailsDao.fetchOneByProjectName(projectName) ?: return null
        return CromProject(projectDetails.uuid, projectDetails.securityId, projectDetails.projectName)
    }

    @Caching(evict = arrayOf(
        CacheEvict("projectById", key = "#result.projectUid.toString()"),
        CacheEvict("projectByName", key = "#name")
    ))
    override fun createProject(name: String): CromProject {
        val projectDetails = Tables.PROJECT_DETAILS
        val record = dslContext
            .insertInto(projectDetails, projectDetails.PROJECT_NAME)
            .values(name)
            .returning(projectDetails.fields().toList())
            .fetchOne()
            .into(projectDetails)

        return record.toCromProject()
    }

    @Cacheable("projectById", key="#uid.toString()")
    override fun findProject(uid: UUID): CromProject? {
        val projectDetails = projectDetailsDao.fetchOneByUuid(uid) ?: return null
        return projectDetails.toCromProject()
    }

    fun ProjectDetails.toCromProject(): CromProject {
        return CromProject(this.uuid, this.securityId, this.projectName)
    }

    fun ProjectDetailsRecord.toCromProject(): CromProject {
        return CromProject(this.uuid, this.securityId, this.projectName)
    }
}
