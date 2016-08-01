package tech.crom.database.impl

import io.ehdev.conrad.db.Tables
import io.ehdev.conrad.db.tables.daos.ProjectDetailsDao
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tech.crom.database.api.ProjectManager
import tech.crom.model.project.CromProject
import java.util.*

@Service
class DefaultProjectManager @Autowired constructor(
    val dslContext: DSLContext,
    val projectDetailsDao: ProjectDetailsDao
): ProjectManager {

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

        return CromProject(record.uuid, record.securityId, record.projectName)
    }

    override fun findProject(uid: UUID): CromProject? {
        val projectDetails = projectDetailsDao.fetchOneByUuid(uid) ?: return null
        return CromProject(projectDetails.uuid, projectDetails.securityId, projectDetails.projectName)
    }
}
