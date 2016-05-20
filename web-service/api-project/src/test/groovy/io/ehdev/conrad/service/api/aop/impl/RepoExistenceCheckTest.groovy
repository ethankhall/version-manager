package io.ehdev.conrad.service.api.aop.impl

import io.ehdev.conrad.database.api.RepoManagementApi
import io.ehdev.conrad.database.model.repo.RequestDetails
import io.ehdev.conrad.database.model.repo.details.ResourceDetails
import io.ehdev.conrad.database.model.repo.details.ResourceId
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


        def successful = createResourceDetails('project', 'found')
        def fail = createResourceDetails('project', 'not-found')

        when:
        proxy.required(new RequestDetails(null, successful))

        then:
        1 * repoManagementApi.doesRepoExist(successful) >> true

        when:
        proxy.required(new RequestDetails(null, fail))

        then:
        1 * repoManagementApi.doesRepoExist(fail) >> false
        def e = thrown(RuntimeException)
        e.message == 'Repo (not-found) doesn\'t exist, but it is required.'
    }

    def 'can match repo that is required to be missing, will throw if exists'() {
        given:
        FooTestInterface target = new FooTestInterfaceImpl()
        AspectJProxyFactory factory = new AspectJProxyFactory(target);
        factory.addAspect(repoExistenceCheck)
        FooTestInterface proxy = factory.getProxy()


        def successful = createResourceDetails('project', 'found')
        def fail = createResourceDetails('project', 'not-found')
        when:
        proxy.missing(new RequestDetails(null, successful))

        then:
        1 * repoManagementApi.doesRepoExist(successful) >> true
        def e = thrown(RuntimeException)
        e.message == 'Repo (found) exists, but it is required not to.'

        when:
        proxy.missing(new RequestDetails(null, fail))

        then:
        1 * repoManagementApi.doesRepoExist(fail) >> false
    }

    ResourceDetails createResourceDetails(String projectName, String repoName) {
        return new ResourceDetails(new ResourceId(projectName, null), new ResourceId(repoName, null))
    }

    private interface FooTestInterface {
        void required(RequestDetails container)
        void missing(RequestDetails container)
    }

    private class FooTestInterfaceImpl implements FooTestInterface {
        @RepoRequired(exists = true)
        void required(RequestDetails container) {

        }

        @RepoRequired(exists = false)
        void missing(RequestDetails container) {

        }
    }
}
