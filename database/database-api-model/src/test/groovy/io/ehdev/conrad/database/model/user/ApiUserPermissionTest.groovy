package io.ehdev.conrad.database.model.user

import spock.lang.Specification

class ApiUserPermissionTest extends Specification{

    def 'expected values'() {
        expect:
        ApiUserPermission.NONE.securityId == 0
        ApiUserPermission.READ.securityId == 1
        ApiUserPermission.WRITE.securityId == 2
        ApiUserPermission.ADMIN.securityId == 4

        ApiUserPermission.NONE < ApiUserPermission.READ
        ApiUserPermission.READ < ApiUserPermission.WRITE
        ApiUserPermission.WRITE < ApiUserPermission.ADMIN

        ApiUserPermission.READ.isHigherOrEqualTo(ApiUserPermission.NONE)
        ApiUserPermission.WRITE.isHigherOrEqualTo(ApiUserPermission.READ)
        ApiUserPermission.ADMIN.isHigherOrEqualTo(ApiUserPermission.WRITE)
    }
}
