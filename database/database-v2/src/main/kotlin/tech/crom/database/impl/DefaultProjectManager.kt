package tech.crom.database.impl

import io.ehdev.conrad.db.Tables
import io.ehdev.conrad.db.tables.ProjectDetailsTable
import io.ehdev.conrad.db.tables.daos.ProjectDetailsDao
import io.ehdev.conrad.db.tables.pojos.ProjectDetails
import io.ehdev.conrad.db.tables.records.ProjectDetailsRecord
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tech.crom.database.api.ProjectManager
import tech.crom.model.project.CromProject
import tech.crom.model.project.FilteredProjects
import tech.crom.model.user.CromUser
import java.util.*

@Service
class DefaultProjectManager @Autowired constructor(
    val dslContext: DSLContext,
    val projectDetailsDao: ProjectDetailsDao
) : ProjectManager {

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

    override fun findProject(projectName: String): CromProject? {
        val projectDetails = projectDetailsDao.fetchOneByProjectName(projectName) ?: return null
        return CromProject(projectDetails.uuid, projectDetails.securityId, projectDetails.projectName)
    }

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
