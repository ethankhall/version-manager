package io.ehdev.conrad.service.api.aop.impl

import io.ehdev.conrad.database.api.RepoManagementApi
import io.ehdev.conrad.database.model.ApiParameterContainer
import io.ehdev.conrad.service.api.aop.annotation.RepoRequired
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory
import org.springframework.core.env.Environment
import org.springframework.mock.env.MockEnvironment
import spock.lang.Specification

class RepoExistenceCheckTest extends Specification {

    Environment environment
    RepoManagementApi repoManagementApi
    RepoExistenceCheck repoExistenceCheck

    def setup() {
        environment = new MockEnvironment()
        repoManagementApi = Mock(RepoManagementApi)
        repoExistenceCheck = new RepoExistenceCheck(environment, repoManagementApi)
    }

    def 'can match repo that is required, will throw if not exists'() {
        given:
        FooTestInterface target = new FooTestInterfaceImpl()
        AspectJProxyFactory factory = new AspectJProxyFactory(target);
        factory.addAspect(repoExistenceCheck)
        FooTestInterface proxy = factory.getProxy()


        def successful = new ApiParameterContainer(null, 'project', 'name')
        def fail = new ApiParameterContainer(null, 'project', 'not')
        when:
        proxy.required(successful)

        then:
        1 * repoManagementApi.doesRepoExist(successful) >> true

        when:
        proxy.required(fail)

        then:
        1 * repoManagementApi.doesRepoExist(fail) >> false
        def e = thrown(RuntimeException)
        e.message == 'Repo (project/not) doesn\'t exist, but it is required.'
    }

    def 'can match repo that is required to be missing, will throw if exists'() {
        given:
        FooTestInterface target = new FooTestInterfaceImpl()
        AspectJProxyFactory factory = new AspectJProxyFactory(target);
        factory.addAspect(repoExistenceCheck)
        FooTestInterface proxy = factory.getProxy()


        def successful = new ApiParameterContainer(null, 'project', 'name')
        def fail = new ApiParameterContainer(null, 'project', 'not')
        when:
        proxy.missing(successful)

        then:
        1 * repoManagementApi.doesRepoExist(successful) >> true
        def e = thrown(RuntimeException)
        e.message == 'Repo (project/name) exists, but it is required not to.'

        when:
        proxy.missing(fail)

        then:
        1 * repoManagementApi.doesRepoExist(fail) >> false
    }

    private interface FooTestInterface {
        void required(ApiParameterContainer container)
        void missing(ApiParameterContainer container)
    }

    private class FooTestInterfaceImpl implements FooTestInterface {
        @RepoRequired(exists = true)
        void required(ApiParameterContainer container) {

        }

        @RepoRequired(exists = false)
        void missing(ApiParameterContainer container) {

        }
    }
}
