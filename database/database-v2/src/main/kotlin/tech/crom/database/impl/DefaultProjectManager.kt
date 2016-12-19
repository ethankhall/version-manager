package tech.crom.database.impl

import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
import org.springframework.stereotype.Service
import tech.crom.database.api.ProjectManager
import tech.crom.db.Tables
import tech.crom.db.tables.ProjectDetailsTable
import tech.crom.db.tables.records.ProjectDetailsRecord
import tech.crom.model.project.CromProject
import tech.crom.model.project.FilteredProjects

@Service
open class DefaultProjectManager @Autowired constructor(
    val dslContext: DSLContext
) : ProjectManager {

    @Caching(evict = arrayOf(
        CacheEvict("projectByName", key = "#project.projectName"),
        CacheEvict("projectById", key = "#project.projectId")
    ))
    override fun deleteProject(project: CromProject) {
        val watcher = Tables.WATCHER
        dslContext
            .deleteFrom(watcher)
            .where(watcher.PROJECT_DETAIL_ID.eq(project.projectId))
            .execute()

        val details = Tables.PROJECT_DETAILS
        dslContext
            .deleteFrom(details)
            .where(details.PRODUCT_DETAIL_ID.eq(project.projectId))
            .execute()
    }

    override fun findProjects(offset: Int, size: Int): FilteredProjects {
        val filteredProjects = dslContext.selectFrom(ProjectDetailsTable.PROJECT_DETAILS)
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
        val details = ProjectDetailsTable.PROJECT_DETAILS
        val projectDetails = dslContext
            .selectFrom(details)
            .where(details.PROJECT_NAME.eq(projectName))
            .fetchOne()?.into(details) ?: return null

        return CromProject(projectDetails.productDetailId, projectDetails.securityId, projectDetails.projectName)
    }

    @Caching(evict = arrayOf(
        CacheEvict("projectById", key = "#result.projectId"),
        CacheEvict("projectByName", key = "#name")
    ))
    override fun createProject(name: String): CromProject {
        val securityId = dslContext
            .insertInto(Tables.SECURITY_ID_SEQ, Tables.SECURITY_ID_SEQ.TYPE)
            .values("project")
            .returning(Tables.SECURITY_ID_SEQ.SECURITY_ID)
            .fetchOne()
            .securityId

        val projectDetails = Tables.PROJECT_DETAILS
        val record = dslContext
            .insertInto(projectDetails, projectDetails.PROJECT_NAME, projectDetails.SECURITY_ID)
            .values(name, securityId)
            .returning()
            .fetchOne()
            .into(projectDetails)

        return record.toCromProject()
    }

    @Cacheable("projectById", key = "#id")
    override fun findProject(id: Long): CromProject? {
        val details = ProjectDetailsTable.PROJECT_DETAILS
        val projectDetails = dslContext
            .selectFrom(details)
            .where(details.PRODUCT_DETAIL_ID.eq(id))
            .fetchOne()?.into(details) ?: return null

        return projectDetails.toCromProject()
    }

    fun ProjectDetailsRecord.toCromProject(): CromProject {
        return CromProject(this.productDetailId, this.securityId, this.projectName)
    }
}
