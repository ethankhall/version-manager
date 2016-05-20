package io.ehdev.conrad.service.api.aop.impl

import io.ehdev.conrad.database.api.PermissionManagementApi
import io.ehdev.conrad.database.model.permission.UserApiAuthentication
import io.ehdev.conrad.database.model.repo.RequestDetails
import io.ehdev.conrad.database.model.repo.details.AuthUserDetails
import io.ehdev.conrad.database.model.repo.details.ResourceDetails
import io.ehdev.conrad.database.model.user.ApiUserPermission
import io.ehdev.conrad.database.model.user.ApiUserPermissionDetails
import io.ehdev.conrad.database.model.user.UserPermissionGrants
import io.ehdev.conrad.service.api.aop.annotation.AdminPermissionRequired
import io.ehdev.conrad.service.api.aop.annotation.ReadPermissionRequired
import io.ehdev.conrad.service.api.aop.annotation.WritePermissionRequired
import io.ehdev.conrad.service.api.aop.exception.PermissionDeniedException
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory
import org.springframework.core.env.Environment
import org.springframework.mock.env.MockEnvironment
import spock.lang.Specification

class PermissionRequiredCheckTest extends Specification {

    PermissionRequiredCheck permissionRequiredCheck
    PermissionManagementApi permissionManagementApi
    Environment environment;

    def setup() {
        permissionManagementApi = new PermissionManagementApi() {
            @Override
            ApiUserPermission findHighestUserPermission(UUID userId, ResourceDetails resourceDetails) {
                return ApiUserPermission.ADMIN
            }

            @Override
            ApiUserPermission findHighestUserPermission(AuthUserDetails authUserDetails, ResourceDetails resourceDetails) {
                return ApiUserPermission.ADMIN
            }

            @Override
            boolean addPermission(AuthUserDetails authUserDetails, ResourceDetails resourceDetails, ApiUserPermission permission) {
                return false
            }

            @Override
            boolean addPermission(String userName, ResourceDetails resourceDetails, ApiUserPermission permission) {
                return false
            }

            @Override
            List<ApiUserPermissionDetails> getPermissions(ResourceDetails resourceDetails) {
                return null
            }

            @Override
            UserPermissionGrants getPermissions(AuthUserDetails authUserDetails) {
                return new UserPermissionGrants([], [])
            }
        }
        environment = new MockEnvironment()
        permissionRequiredCheck = new PermissionRequiredCheck(permissionManagementApi, environment)
    }

    RequestDetails createRequestDetails(ApiUserPermission apiUserPermission) {
        def details = new AuthUserDetails(UUID.randomUUID(), apiUserPermission.toString(), apiUserPermission, null)
        return new RequestDetails(details, null)
    }

    def 'admin permissions work'() {
        given:
        FooTestInterface target = new FooTestInterfaceImpl()
        AspectJProxyFactory factory = new AspectJProxyFactory(target);
        factory.addAspect(permissionRequiredCheck)
        FooTestInterface proxy = factory.getProxy()

        when:
        proxy.adminPermissions(createRequestDetails(ApiUserPermission.READ), 'foo')

        then:
        thrown(PermissionDeniedException)

        when:
        proxy.adminPermissions(createRequestDetails(ApiUserPermission.WRITE), 'b')

        then:
        thrown(PermissionDeniedException)

        when:
        proxy.adminPermissions(createRequestDetails(ApiUserPermission.ADMIN), 'c')

        then:
        noExceptionThrown()
    }

    def 'read permissions work'() {
        given:
        FooTestInterface target = new FooTestInterfaceImpl()
        AspectJProxyFactory factory = new AspectJProxyFactory(target);
        factory.addAspect(permissionRequiredCheck)
        FooTestInterface proxy = factory.getProxy()
        def apiUser = new UserApiAuthentication(UUID.randomUUID(), 'username', 'name', 'email')

        when:
        proxy.readPermissions(createRequestDetails(ApiUserPermission.READ))

        then:
        noExceptionThrown()

        when:
        proxy.readPermissions(createRequestDetails(ApiUserPermission.WRITE))

        then:
        noExceptionThrown()

        when:
        proxy.readPermissions(createRequestDetails(ApiUserPermission.ADMIN))

        then:
        noExceptionThrown()
    }

    def 'write permissions work'() {
        given:
        FooTestInterface target = new FooTestInterfaceImpl()
        AspectJProxyFactory factory = new AspectJProxyFactory(target);
        factory.addAspect(permissionRequiredCheck)
        FooTestInterface proxy = factory.getProxy()
        def apiUser = new UserApiAuthentication(UUID.randomUUID(), 'username', 'name', 'email')

        when:
        proxy.writePermissions(createRequestDetails(ApiUserPermission.READ))

        then:
        thrown(PermissionDeniedException)

        when:
        proxy.writePermissions(createRequestDetails(ApiUserPermission.WRITE))

        then:
        noExceptionThrown()

        when:
        proxy.writePermissions(createRequestDetails(ApiUserPermission.ADMIN))

        then:
        noExceptionThrown()
    }

    def 'will throw when parameters do not match'() {
        given:
        FooTestInterface target = new FooTestInterfaceImpl()
        AspectJProxyFactory factory = new AspectJProxyFactory(target);
        factory.addAspect(permissionRequiredCheck)
        FooTestInterface proxy = factory.getProxy()

        when:
        proxy.nonConformingParameter('asdf')

        then:
        def e = thrown(RuntimeException)
        e.message == 'Unable to find RequestDetails on FooTestInterface.nonConformingParameter(..)'
    }

    private interface FooTestInterface {
        void adminPermissions(RequestDetails container, String foo)
        void writePermissions(RequestDetails container)
        void readPermissions(RequestDetails container)
        void nonConformingParameter(String somethingElse)
    }

    private class FooTestInterfaceImpl implements FooTestInterface {
        @ReadPermissionRequired
        public void readPermissions(RequestDetails container) {

        }

        @Override
        @AdminPermissionRequired
        void nonConformingParameter(String somethingElse) {

        }

        @WritePermissionRequired
        public void writePermissions(RequestDetails container) {

        }

        @AdminPermissionRequired
        public void adminPermissions(RequestDetails container, String foo) {
            1 + 1;
        }
    }
}
