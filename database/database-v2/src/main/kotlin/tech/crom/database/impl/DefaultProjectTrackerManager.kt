package tech.crom.database.impl

import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tech.crom.database.api.ProjectTrackerManager
import tech.crom.db.Tables
import tech.crom.model.project.CromProject
import tech.crom.model.user.CromUser

@Service
open class DefaultProjectTrackerManager @Autowired constructor(
    val dsl: DSLContext
) : ProjectTrackerManager {

    override fun count(cromUser: CromUser): Int {
        val pdt = Tables.PROJECT_DETAIL_TRACKER
        return dsl
            .selectCount()
            .from(pdt)
            .where(pdt.USER_ID.eq(cromUser.userId))
            .fetchOne(0, Int::class.java)
    }

    override fun link(cromProject: CromProject, cromUser: CromUser) {
        val pdt = Tables.PROJECT_DETAIL_TRACKER
        dsl
            .insertInto(pdt, pdt.PRODUCT_DETAIL_ID, pdt.USER_ID)
            .values(cromProject.projectId, cromUser.userId)
            .execute()
    }

    override fun unlink(cromProject: CromProject) {
        val pdt = Tables.PROJECT_DETAIL_TRACKER
        dsl
            .deleteFrom(pdt)
            .where(pdt.PRODUCT_DETAIL_ID.eq(cromProject.projectId))
            .execute()
    }
}
