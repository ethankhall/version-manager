package io.ehdev.conrad.service.api.aop.impl

import io.ehdev.conrad.service.api.aop.annotation.LoggedInUserRequired
import io.ehdev.conrad.service.api.aop.exception.NonUserNotAllowedException
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory
import org.springframework.mock.env.MockEnvironment
import spock.lang.Specification
import tech.crom.model.security.authorization.CromPermission
import tech.crom.model.user.CromUser
import tech.crom.web.api.model.RequestDetails

class LoggedInUserCheckTest extends Specification {

    LoggedInUserCheck loggedInUserCheck

    def setup() {
        def environment = new MockEnvironment()
        loggedInUserCheck = new LoggedInUserCheck(environment)
    }

    def 'admin permissions work'() {
        given:
        FooTestInterface target = new FooTestInterfaceImpl()
        AspectJProxyFactory factory = new AspectJProxyFactory(target);
        factory.addAspect(loggedInUserCheck)
        FooTestInterface proxy = factory.getProxy()

        when:
        def permissions = new RequestDetails.RequestPermissions(CromPermission.NONE, CromPermission.NONE, null)
        proxy.doWork(new RequestDetails(null, null, permissions, new RequestDetails.RawRequestDetails('', [:])))


        then:
        thrown(NonUserNotAllowedException)

        when:
        def user = new CromUser(UUID.randomUUID(), 'username', 'display')
        permissions = new RequestDetails.RequestPermissions(CromPermission.NONE, CromPermission.ADMIN, user)
        proxy.doWork(new RequestDetails(null, null, permissions, new RequestDetails.RawRequestDetails('', [:])))

        then:
        noExceptionThrown()
    }

    private interface FooTestInterface {
        public void doWork(RequestDetails container)
    }

    private class FooTestInterfaceImpl implements FooTestInterface {

        @Override
        @LoggedInUserRequired
        void doWork(RequestDetails container) {

        }
    }

}
