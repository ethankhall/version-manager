package io.ehdev.conrad.service.api.aop.impl

import io.ehdev.conrad.database.api.PermissionManagementApi
import io.ehdev.conrad.database.model.ApiParameterContainer
import io.ehdev.conrad.database.model.user.ApiUser
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
            boolean doesUserHavePermission(ApiUser apiUser, String project, String repoName, ApiUserPermission permission) {
                return ApiUserPermission.valueOf(project.toUpperCase()) >= permission
            }

            @Override
            boolean addPermission(String username, ApiUser authenticatedUser, String projectName, String repoName, ApiUserPermission permission) {
                return false
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

        when:
        proxy.adminPermissions(new ApiParameterContainer(null, 'read', null), 'a')

        then:
        thrown(PermissionDeniedException)

        when:
        proxy.adminPermissions(new ApiParameterContainer(null, 'write', null), 'b')

        then:
        thrown(PermissionDeniedException)

        when:
        proxy.adminPermissions(new ApiParameterContainer(null, 'admin', null), 'c')

        then:
        noExceptionThrown()
    }

    def 'read permissions work'() {
        given:
        FooTestInterface target = new FooTestInterfaceImpl()
        AspectJProxyFactory factory = new AspectJProxyFactory(target);
        factory.addAspect(permissionRequiredCheck)
        FooTestInterface proxy = factory.getProxy()

        when:
        proxy.readPermissions(new ApiParameterContainer(null, 'read', null))

        then:
        noExceptionThrown()

        when:
        proxy.readPermissions(new ApiParameterContainer(null, 'write', null))

        then:
        noExceptionThrown()

        when:
        proxy.readPermissions(new ApiParameterContainer(null, 'admin', null))

        then:
        noExceptionThrown()
    }

    def 'write permissions work'() {
        given:
        FooTestInterface target = new FooTestInterfaceImpl()
        AspectJProxyFactory factory = new AspectJProxyFactory(target);
        factory.addAspect(permissionRequiredCheck)
        FooTestInterface proxy = factory.getProxy()

        when:
        proxy.writePermissions(new ApiParameterContainer(null, 'read', null))

        then:
        thrown(PermissionDeniedException)

        when:
        proxy.writePermissions(new ApiParameterContainer(null, 'write', null))

        then:
        noExceptionThrown()

        when:
        proxy.writePermissions(new ApiParameterContainer(null, 'admin', null))

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
