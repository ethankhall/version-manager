package io.ehdev.conrad.service.api.aop.impl

import io.ehdev.conrad.database.api.PermissionManagementApi
import io.ehdev.conrad.database.model.ApiParameterContainer
import io.ehdev.conrad.database.model.permission.ApiTokenAuthentication
import io.ehdev.conrad.database.model.permission.UserApiAuthentication
import io.ehdev.conrad.database.model.user.ApiRepoUserPermission
import io.ehdev.conrad.database.model.user.ApiUserPermission
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
            boolean doesUserHavePermission(ApiTokenAuthentication apiUser, String project, String repoName, ApiUserPermission permission) {
                return ApiUserPermission.valueOf(project.toUpperCase()) >= permission
            }

            @Override
            boolean addPermission(String username, ApiTokenAuthentication authenticatedUser, String projectName, String repoName, ApiUserPermission permission) {
                return false
            }

            @Override
            boolean forceAddPermission(String username, String projectName, String repoName, ApiUserPermission permission) {
                return false
            }

            @Override
            List<ApiRepoUserPermission> getPermissionsForProject(ApiParameterContainer repoModel) {
                return new ArrayList<ApiRepoUserPermission>()
            }
        }
        environment = new MockEnvironment()
        permissionRequiredCheck = new PermissionRequiredCheck(permissionManagementApi, environment)
    }

    def 'admin permissions work'() {
        given:
        FooTestInterface target = new FooTestInterfaceImpl()
        AspectJProxyFactory factory = new AspectJProxyFactory(target);
        factory.addAspect(permissionRequiredCheck)
        FooTestInterface proxy = factory.getProxy()
        def apiUser = new UserApiAuthentication(UUID.randomUUID(), 'username', 'name', 'email')

        when:
        proxy.adminPermissions(new ApiParameterContainer(apiUser, 'read', null), 'a')

        then:
        thrown(PermissionDeniedException)

        when:
        proxy.adminPermissions(new ApiParameterContainer(apiUser, 'write', null), 'b')

        then:
        thrown(PermissionDeniedException)

        when:
        proxy.adminPermissions(new ApiParameterContainer(apiUser, 'admin', null), 'c')

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
        proxy.readPermissions(new ApiParameterContainer(apiUser, 'read', null))

        then:
        noExceptionThrown()

        when:
        proxy.readPermissions(new ApiParameterContainer(apiUser, 'write', null))

        then:
        noExceptionThrown()

        when:
        proxy.readPermissions(new ApiParameterContainer(apiUser, 'admin', null))

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
        proxy.writePermissions(new ApiParameterContainer(apiUser, 'read', null))

        then:
        thrown(PermissionDeniedException)

        when:
        proxy.writePermissions(new ApiParameterContainer(apiUser, 'write', null))

        then:
        noExceptionThrown()

        when:
        proxy.writePermissions(new ApiParameterContainer(apiUser, 'admin', null))

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
            e.message == 'Unable to find ApiParameterContainer on FooTestInterface.nonConformingParameter(..)'
        }

    private interface FooTestInterface {
        void adminPermissions(ApiParameterContainer container, String foo)
        void writePermissions(ApiParameterContainer container)
        void readPermissions(ApiParameterContainer container)
        void nonConformingParameter(String somethingElse)
    }

    private class FooTestInterfaceImpl implements FooTestInterface {
        @ReadPermissionRequired
        public void readPermissions(ApiParameterContainer container) {

        }

        @Override
        @AdminPermissionRequired
        void nonConformingParameter(String somethingElse) {

        }

        @WritePermissionRequired
        public void writePermissions(ApiParameterContainer container) {

        }

        @AdminPermissionRequired
        public void adminPermissions(ApiParameterContainer container, String foo) {
            1 + 1;
        }
    }
}
