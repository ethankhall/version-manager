package io.ehdev.conrad.app

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import groovy.transform.TupleConstructor
import io.ehdev.conrad.db.tables.*
import org.apache.commons.lang3.StringUtils
import org.apache.http.StatusLine
import org.apache.http.client.fluent.Request
import org.apache.http.entity.ContentType
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.env.Environment
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.annotation.Commit
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Stepwise
import tech.crom.business.api.TokenManagementApi
import tech.crom.database.api.UserManager
import tech.crom.model.token.GeneratedTokenDetails
import tech.crom.model.user.CromUser

import java.time.ZonedDateTime

@Stepwise
@WithMockUser
@ContextConfiguration
@TestPropertySource("/application-test.yml")
@SpringBootTest(classes = [TestWebAppConfiguration], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LongWindedApiIntegrationTest extends Specification {

    @Autowired
    Environment environment

    @Autowired
    UserManager userManager

    @Autowired
    TokenManagementApi tokenManagementApi

    @Autowired
    DSLContext context

    @Shared
    def slurper = new JsonSlurper()

    @Shared
    UserContainer userContainer1

    @Shared
    UserContainer userContainer2

    @Commit
    def 'clean up env'() {
        expect:
        [AclEntryTable, AclObjectIdentityTable, AclClassTable, AclSidTable].each {
            context.truncate(it.newInstance()).cascade().execute()
        }

        [UserTokensTable, UserDetailsTable].each {
            context.truncate(it.newInstance()).cascade().execute()
        }

        [SsUserconnectionTable].each {
            context.truncate(it.newInstance()).cascade().execute()
        }

        [CommitMetadataTable, CommitDetailsTable, RepositoryTokensTable, RepoDetailsTable, ProjectDetailsTable].each {
            context.truncate(it.newInstance()).cascade().execute()
        }
    }

    def 'create users for test'() {

        when:
        CromUser user1 = userManager.createUser("displayName", "user1")
        CromUser user2 = userManager.createUser("displayName", "user2")

        then:
        user1
        user2

        when:
        userContainer1 = new UserContainer(user1, tokenManagementApi.createToken(user1, ZonedDateTime.now().plusMinutes(1)))
        userContainer2 = new UserContainer(user2, tokenManagementApi.createToken(user2, ZonedDateTime.now().plusMinutes(1)))

        then:
        userContainer1.tokenDetails
        userContainer1.user
        userContainer2.tokenDetails
        userContainer2.user
    }

    def 'create a project for each user'() {
        when:
        def response = makeRequest('api/v1/project/repoUser1')

        then:
        response.statusLine.statusCode == 404

        when:
        def content = ["scmUrl": "git@github.com:foo/bar.git", "bumper": "semver"]
        response = makeRequest("api/v1/project/repoUser1", content, userContainer1)

        then:
        response.statusLine.statusCode == 201
        response.content.name == 'repoUser1'

        when:
        content = ["scmUrl": "git@github.com:foo/bar.git", "bumper": "semver"]
        response = makeRequest("api/v1/project/repoUser2", content, userContainer2)

        then:
        response.statusLine.statusCode == 201
        response.content.name == 'repoUser2'
    }

    def 'get response from each repo'() {
        when:
        def response = makeRequest('api/v1/project/repoUser1')

        then:
        response.statusLine.statusCode == 200
        response.content.name == 'repoUser1'
        response.content.links.find { it.rel == 'tokens' } == null
        response.content.links.find { it.rel == 'permissions' } == null

        when:
        response = makeRequest('api/v1/project/repoUser2')

        then:
        response.statusLine.statusCode == 200
        response.content.name == 'repoUser2'
        response.content.links.find { it.rel == 'tokens' } == null
        response.content.links.find { it.rel == 'permissions' } == null

        when:
        response = makeRequest('api/v1/project/repoUser1', userContainer1)

        then:
        response.statusLine.statusCode == 200
        response.content.name == 'repoUser1'
        response.content.links.find { it.rel == 'tokens' } != null
        response.content.links.find { it.rel == 'permissions' } != null

        when:
        response = makeRequest('api/v1/project/repoUser1', userContainer2) //unapproved user

        then:
        response.statusLine.statusCode == 200
        response.content.name == 'repoUser1'
        response.content.links.find { it.rel == 'tokens' } == null
        response.content.links.find { it.rel == 'permissions' } == null

        when:
        response = makeRequest('api/v1/project/repoUser2', userContainer2)

        then:
        response.statusLine.statusCode == 200
        response.content.name == 'repoUser2'
        response.content.links.find { it.rel == 'tokens' } != null
        response.content.links.find { it.rel == 'permissions' } != null

        when:
        response = makeRequest('api/v1/project/repoUser2', userContainer1) //unapproved user

        then:
        response.statusLine.statusCode == 200
        response.content.name == 'repoUser2'
        response.content.links.find { it.rel == 'tokens' } == null
        response.content.links.find { it.rel == 'permissions' } == null
    }

    def 'grant and remove permission to user'() {
        when:
        def response = makeRequest('api/v1/project/repoUser1/permissions')

        then:
        response.statusLine.statusCode == 418

        when:
        response = makeRequest('api/v1/project/repoUser1/permissions', userContainer1)

        then:
        response.statusLine.statusCode == 200
        (response.content.permissions as List)[0] == [username: 'user1', permission: 'ADMIN']

        //======================================
        //== Give the other user READ permissions
        //======================================
        when:
        def permissionGrant = ["username": "user2", "permission": "READ"]
        response = makeRequest('api/v1/project/repoUser1/permissions', permissionGrant, userContainer1)

        then:
        response.statusLine.statusCode == 201
        response.content.accepted == true

        when:
        response = makeRequest('api/v1/project/repoUser1/permissions', userContainer1)

        then:
        (response.content.permissions as List).find { it.username == 'user1' } == [username: 'user1', permission: 'ADMIN']
        (response.content.permissions as List).find { it.username == 'user2' } == [username: 'user2', permission: 'READ']
        (response.content.permissions as List).size() == 2

        when:  //user doesn't have enough permissions
        response = makeRequest('api/v1/project/repoUser1/permissions', userContainer2)

        then:
        response.statusLine.statusCode == 401
        response.content.errorCode == 'PD-001'
        response.content.message == 'user2 does not have access.'

        //======================================
        //== Give the other user WRITE permissions
        //======================================
        when:
        permissionGrant = ["username": "user2", "permission": "WRITE"]
        response = makeRequest('api/v1/project/repoUser1/permissions', permissionGrant, userContainer1)

        then:
        response.statusLine.statusCode == 201
        response.content.accepted == true

        when:
        response = makeRequest('api/v1/project/repoUser1/permissions', userContainer1)

        then:
        (response.content.permissions as List).find { it.username == 'user1' } == [username: 'user1', permission: 'ADMIN']
        (response.content.permissions as List).find { it.username == 'user2' } == [username: 'user2', permission: 'WRITE']
        (response.content.permissions as List).size() == 2

        when:  //user doesn't have enough permissions
        response = makeRequest('api/v1/project/repoUser1/permissions', userContainer2)

        then:
        response.statusLine.statusCode == 401
        response.content.errorCode == 'PD-001'
        response.content.message == 'user2 does not have access.'

        //======================================
        //== Give the other user READ permissions
        //======================================
        when:
        permissionGrant = ["username": "user2", "permission": "ADMIN"]
        response = makeRequest('api/v1/project/repoUser1/permissions', permissionGrant, userContainer1)

        then:
        response.statusLine.statusCode == 201
        response.content.accepted == true

        when:
        response = makeRequest('api/v1/project/repoUser1/permissions', userContainer1)

        then:
        (response.content.permissions as List).find { it.username == 'user1' } == [username: 'user1', permission: 'ADMIN']
        (response.content.permissions as List).find { it.username == 'user2' } == [username: 'user2', permission: 'ADMIN']
        (response.content.permissions as List).size() == 2

        when:
        response = makeRequest('api/v1/project/repoUser1/permissions', userContainer2)

        then:
        (response.content.permissions as List).find { it.username == 'user1' } == [username: 'user1', permission: 'ADMIN']
        (response.content.permissions as List).find { it.username == 'user2' } == [username: 'user2', permission: 'ADMIN']
        (response.content.permissions as List).size() == 2

        //======================================
        //== Make user delete their own privileges
        //======================================

        when:
        response = doDelete('api/v1/project/repoUser1/permissions/user2', userContainer2)

        then:
        response.statusLine.statusCode == 200

        when:
        response = makeRequest('api/v1/project/repoUser1/permissions', userContainer1)

        then:
        (response.content.permissions as List).find { it.username == 'user1' } == [username: 'user1', permission: 'ADMIN']
        (response.content.permissions as List).size() == 1

        when:  //user doesn't have enough permissions
        response = makeRequest('api/v1/project/repoUser1/permissions', userContainer2)

        then:
        response.statusLine.statusCode == 401
        response.content.errorCode == 'PD-001'
        response.content.message == 'user2 does not have access.'
    }

    def doDelete(String endpoint, UserContainer container) {
        def response = Request
            .Delete("http://localhost:${environment.getProperty("local.server.port")}/$endpoint")
            .addHeader("X-AUTH-TOKEN", container.tokenDetails.value)
            .execute().returnResponse()

        def responseString = response.entity.content.text ?: ""
        def responseCode = response.statusLine

        return new PostResponse(StringUtils.isEmpty(responseString)? null : (slurper.parseText(responseString) as Map), responseCode)
    }

    def makeRequest(String endpoint, UserContainer container = null) {
        def builder = Request.Get("http://localhost:${environment.getProperty("local.server.port")}/$endpoint")
        if (container) {
            builder = builder.addHeader("X-AUTH-TOKEN", container.tokenDetails.value)
        }
        def response = builder.execute().returnResponse()

        def responseString = response.entity.content.text
        def responseCode = response.statusLine

        return new PostResponse(slurper.parseText(responseString) as Map, responseCode)
    }

    def makeRequest(String endpoint, Object body, UserContainer container) {
        def builder = new JsonBuilder(body)
        def response = Request.Post("http://localhost:${environment.getProperty("local.server.port")}/$endpoint")
            .addHeader("X-AUTH-TOKEN", container.tokenDetails.value)
            .bodyString(builder.toString(), ContentType.APPLICATION_JSON)
            .execute().returnResponse()
        def responseString = response.entity.content.text
        def responseCode = response.statusLine

        return new PostResponse(slurper.parseText(responseString) as Map, responseCode)
    }

    @TupleConstructor
    static class UserContainer {
        final CromUser user
        final GeneratedTokenDetails tokenDetails
    }

    @TupleConstructor
    static class PostResponse {
        final Map content
        final StatusLine statusLine
    }
}
