package tech.crom.database.impl

import io.ehdev.conrad.db.Tables
import io.ehdev.conrad.db.tables.UserDetailsTable
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import tech.crom.database.api.UserManager
import tech.crom.model.user.CromUser
import java.util.*

@Service
open class DefaultUserManager @Autowired constructor(
    val dslContext: DSLContext
) : UserManager {

    @CacheEvict("userById", key = "#sourceUser.userUid.toString()")
    override fun changeDisplayName(sourceUser: CromUser, displayName: String) {
        val userDetails = Tables.USER_DETAILS
        dslContext
            .update(userDetails)
            .set(userDetails.NAME, displayName)
            .where(userDetails.UUID.eq(sourceUser.userUid))
            .execute()
    }

    override fun findUserDetails(userName: String): CromUser? {
        val details = UserDetailsTable.USER_DETAILS
        val user = dslContext
            .selectFrom(details)
            .where(details.USER_NAME.eq(userName))
            .fetchOne()?.into(details) ?: return null
        return CromUser(user.uuid, user.userName, user.name)
    }

    override fun userNameExists(userName: String): Boolean {
        val userDetails = Tables.USER_DETAILS
        val count = dslContext
            .selectCount()
            .from(userDetails)
            .where(userDetails.USER_NAME.eq(userName))
            .fetchOne(0, Int::class.java)
        return count != 0
    }

    override fun createUser(displayName: String, userName: String): CromUser {
        if (userNameExists(userName)) {
            throw UserManager.UsernameAlreadyExists(userName)
        }

        val userDetails = Tables.USER_DETAILS
        val user = dslContext
            .insertInto(userDetails, userDetails.USER_NAME, userDetails.NAME)
            .values(userName, displayName)
            .returning(userDetails.fields().toList())
            .fetchOne()
            .into(userDetails)

        return CromUser(user.uuid, user.userName, user.name)
    }

    @CacheEvict("userById", key = "#cromUser.userUid.toString()")
    override fun changeUserName(cromUser: CromUser, newUserName: String): CromUser {
        if (userNameExists(newUserName) && cromUser.userName != newUserName) {
            throw UserManager.UsernameAlreadyExists(newUserName)
        }

        val userDetails = Tables.USER_DETAILS
        val user = dslContext
            .update(userDetails)
            .set(userDetails.USER_NAME, newUserName)
            .where(userDetails.UUID.eq(cromUser.userUid))
            .returning(userDetails.fields().toList())
            .fetchOne()
            .into(userDetails)

        return CromUser(user.uuid, user.userName, user.name)
    }

    @Cacheable("userById", key = "#uuid.toString()")
    override fun findUserDetails(uuid: UUID): CromUser? {
        val details = UserDetailsTable.USER_DETAILS
        val user = dslContext
            .selectFrom(details)
            .where(details.UUID.eq(uuid))
            .fetchOne()?.into(details) ?: return null
        return CromUser(user.uuid, user.userName, user.name)
    }
}
