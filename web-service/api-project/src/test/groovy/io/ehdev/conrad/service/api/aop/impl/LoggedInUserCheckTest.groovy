package io.ehdev.conrad.service.api.aop.impl

import io.ehdev.conrad.database.model.repo.RequestDetails
import io.ehdev.conrad.database.model.repo.details.AuthUserDetails
import io.ehdev.conrad.database.model.user.ApiUserPermission
import io.ehdev.conrad.service.api.aop.annotation.LoggedInUserRequired
import io.ehdev.conrad.service.api.aop.exception.UserNotLoggedInException
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory
import org.springframework.mock.env.MockEnvironment
import spock.lang.Specification

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
        proxy.doWork(new RequestDetails(null, null))


        then:
        thrown(UserNotLoggedInException)

        when:
        proxy.doWork(new RequestDetails(new AuthUserDetails(UUID.randomUUID(), ' ', ApiUserPermission.ADMIN, null), null))

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
