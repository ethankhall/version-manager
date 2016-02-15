package io.ehdev.conrad.database.api.internal

import io.ehdev.conrad.database.config.TestConradDatabaseConfig
import io.ehdev.conrad.database.model.user.ApiUserPermission
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

@Rollback
@Transactional
@ContextConfiguration(classes = [TestConradDatabaseConfig], loader = SpringApplicationContextLoader)
class DefaultPermissionManagementApiTest extends Specification {

    @Autowired
    DefaultPermissionManagementApi permissionManagementApi

    @Autowired
    DefaultProjectManagementApi projectManagementApi

    @Autowired
    DefaultRepoManagementApi repoManagementApi

    @Autowired
    DefaultUserManagementApi userManagementApi

    @Autowired
    DSLContext dslContext

    def 'can get user permission for a repo'() {
        given:
        def user = userManagementApi.createUser('userName', 'name', 'email')
        def readUser = userManagementApi.createUser('readUser', 'name', 'email')
        projectManagementApi.createProject('project')

        when:
        def permission = permissionManagementApi.forceAddPermission('userName', 'project', null, ApiUserPermission.ADMIN)

        then:
        permission

        when:
        def readPermissions = permissionManagementApi.forceAddPermission('readUser', 'project', null, ApiUserPermission.READ)

        then:
        readPermissions

        expect:
        permissionManagementApi.doesUserHavePermission(readUser, 'project', null, ApiUserPermission.NONE)
        permissionManagementApi.doesUserHavePermission(readUser, 'project', null, ApiUserPermission.READ)
        !permissionManagementApi.doesUserHavePermission(readUser, 'project', null, ApiUserPermission.WRITE)
        !permissionManagementApi.doesUserHavePermission(readUser, 'project', null, ApiUserPermission.ADMIN)

        permissionManagementApi.doesUserHavePermission(user, 'project', null, ApiUserPermission.NONE)
        permissionManagementApi.doesUserHavePermission(user, 'project', null, ApiUserPermission.READ)
        permissionManagementApi.doesUserHavePermission(user, 'project', null, ApiUserPermission.WRITE)
        permissionManagementApi.doesUserHavePermission(user, 'project', null, ApiUserPermission.ADMIN)
    }
}
