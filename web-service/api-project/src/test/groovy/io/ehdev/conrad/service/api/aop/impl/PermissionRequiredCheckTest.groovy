package io.ehdev.conrad.service.api.aop.impl

import io.ehdev.conrad.service.api.aop.annotation.AdminPermissionRequired
import io.ehdev.conrad.service.api.aop.annotation.ReadPermissionRequired
import io.ehdev.conrad.service.api.aop.annotation.WritePermissionRequired
import io.ehdev.conrad.service.api.aop.exception.PermissionDeniedException
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory
import org.springframework.core.env.Environment
import org.springframework.mock.env.MockEnvironment
import spock.lang.Specification
import tech.crom.model.security.authorization.CromPermission
import tech.crom.security.authorization.api.PermissionService
import tech.crom.web.api.model.RequestDetails

import static io.ehdev.conrad.service.api.service.TestUtils.createTestingRepoModel

class PermissionRequiredCheckTest extends Specification {

    PermissionRequiredCheck permissionRequiredCheck
    PermissionService permissionService
    Environment environment;

    def setup() {
        permissionService = new PermissionServiceTestDouble()
        environment = new MockEnvironment()
        permissionRequiredCheck = new PermissionRequiredCheck(environment, permissionService)
    }

    def 'admin permissions work'() {
        given:
        FooTestInterface target = new FooTestInterfaceImpl()
        AspectJProxyFactory factory = new AspectJProxyFactory(target);
        factory.addAspect(permissionRequiredCheck)
        FooTestInterface proxy = factory.getProxy()

        when:
        proxy.adminPermissions(createTestingRepoModel(CromPermission.NONE, CromPermission.NONE), 'foo')

        then:
        thrown(PermissionDeniedException)

        when:
        proxy.adminPermissions(createTestingRepoModel(CromPermission.WRITE, CromPermission.WRITE), 'b')

        then:
        thrown(PermissionDeniedException)

        when:
        proxy.adminPermissions(createTestingRepoModel(CromPermission.ADMIN, CromPermission.ADMIN), 'c')

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
        proxy.readPermissions(createTestingRepoModel(CromPermission.READ, CromPermission.READ))

        then:
        noExceptionThrown()

        when:
        proxy.readPermissions(createTestingRepoModel(CromPermission.WRITE, CromPermission.WRITE))

        then:
        noExceptionThrown()

        when:
        proxy.readPermissions(createTestingRepoModel(CromPermission.ADMIN, CromPermission.ADMIN))

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
        proxy.writePermissions(createTestingRepoModel(CromPermission.READ, CromPermission.READ))

        then:
        thrown(PermissionDeniedException)

        when:
        proxy.writePermissions(createTestingRepoModel(CromPermission.WRITE, CromPermission.WRITE))

        then:
        noExceptionThrown()

        when:
        proxy.writePermissions(createTestingRepoModel(CromPermission.ADMIN, CromPermission.ADMIN))

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
