package io.ehdev.version.service

import io.ehdev.version.IntegrationTestConfiguration
import io.ehdev.version.TestDataLoader
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification
import spock.lang.Unroll

import javax.transaction.Transactional

@Transactional
@Rollback(true)
@ContextConfiguration(classes = [IntegrationTestConfiguration.class], loader = SpringApplicationContextLoader.class)
class VersionServiceIntegrationTest extends Specification {

    @Autowired
    private TestDataLoader testDataLoader;

    @Autowired
    VersionService versionService

    @Unroll
    def 'can get next version, when commit is #commitId'() {
        setup:
        testDataLoader.loadData()

        when:
        def version = versionService.getVersionForRepo(repo, commitId)

        then:
        noExceptionThrown()
        version.getVersionString() == expectedVersion

        where:
        commitId                             | repo          | expectedVersion
        TestDataLoader.NO_CHILDREN_COMMIT_ID | 'no-children' | '1.2.4-SNAPSHOT'
        TestDataLoader.PARENT_COMMIT_ID      | 'next'        | '1.2.4-SNAPSHOT'
        TestDataLoader.CHILD_COMMIT_ID       | 'next'        | '1.2.5-SNAPSHOT'
        TestDataLoader.BUGFIX_COMMIT_ID      | 'build'       | '1.2.3.2-SNAPSHOT'
    }
}
