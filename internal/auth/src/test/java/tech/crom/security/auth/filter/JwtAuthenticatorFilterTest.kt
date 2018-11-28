package tech.crom.security.auth.filter

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.VerificationException
import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.equalTo
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor
import com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono
import tech.crom.model.security.authorization.CromPermission
import tech.crom.security.auth.AnonymousUser
import tech.crom.security.auth.ApiUser
import tech.crom.security.auth.CromPrincipal
import java.security.Principal


class JwtAuthenticatorFilterTest {

    private lateinit var wireMockServer: WireMockServer

    @BeforeEach
    fun setup() {
        wireMockServer = WireMockServer(WireMockConfiguration.options().port(0))
        wireMockServer.start()
    }

    @AfterEach
    fun teardown() {
        val unmatchedRequests = wireMockServer.findAllUnmatchedRequests()
        if (!unmatchedRequests.isEmpty()) {
            val nearMisses = wireMockServer.findNearMissesForAllUnmatchedRequests()
            if (nearMisses.isEmpty()) {
                throw VerificationException.forUnmatchedRequests(unmatchedRequests)
            } else {
                throw VerificationException.forUnmatchedNearMisses(nearMisses)
            }
        }
        wireMockServer.stop()
    }

    @Test
    fun `on 404 will not find principal`() {
        wireMockServer.stubFor(get(urlEqualTo("/api/v1/user"))
            .willReturn(aResponse().withHeader("Content-Type", "application/json")
                .withStatus(404)))
        val filter = JwtAuthenticatorFilter("http://localhost:${wireMockServer.port()}", jacksonObjectMapper())
        assertTypeEquals<AnonymousUser>(filter.getPrincipal("foo", emptyMap()).block())
    }

    @Test
    fun `when user gives 200, but check gives 404`() {
        wireMockServer.stubFor(get(urlEqualTo("/api/v1/user"))
            .willReturn(aResponse().withHeader("Content-Type", "application/json")
                .withStatus(200)
                .withBody(this.javaClass.getResourceAsStream("/logged-in-user.json").readBytes())))

        wireMockServer.stubFor(get(urlPathEqualTo("/api/v1/check/crom/permission/urn:crom:foo"))
            .willReturn(aResponse().withHeader("Content-Type", "application/json")
                .withStatus(404)))

        val filter = JwtAuthenticatorFilter("http://localhost:${wireMockServer.port()}", jacksonObjectMapper())
        val principal = filter.getPrincipal("foo", mapOf("projectName" to "foo")).block()

        assertIterableEquals(emptyList<CromPermission>(), assertTypeEquals<ApiUser>(principal).permissions)
    }

    @Test
    fun `200s will find principal`() {
        wireMockServer.stubFor(get(urlEqualTo("/api/v1/user"))
            .willReturn(aResponse().withHeader("Content-Type", "application/json")
                .withStatus(200)
                .withBody(this.javaClass.getResourceAsStream("/logged-in-user.json").readBytes())))

        wireMockServer.stubFor(get(urlPathEqualTo("/api/v1/check/crom/permission/urn:crom:foo"))
            .willReturn(aResponse().withHeader("Content-Type", "application/json")
                .withStatus(200)
                .withBody(this.javaClass.getResourceAsStream("/permission-check.json").readBytes())))

        val filter = JwtAuthenticatorFilter("http://localhost:${wireMockServer.port()}", jacksonObjectMapper())
        val principal = filter.getPrincipal("foo", mapOf("projectName" to "foo")).block()

        wireMockServer.verify(1,
            getRequestedFor(urlEqualTo("/api/v1/user")))

        wireMockServer.verify(1,
            getRequestedFor(urlPathEqualTo("/api/v1/check/crom/permission/urn:crom:foo"))
                .withQueryParam("action", equalTo("NONE,READ,WRITE,ADMIN")))

        val user = assertTypeEquals<ApiUser>(principal)

        wireMockServer.verify(1,
            getRequestedFor(urlEqualTo("/api/v1/user")))

        wireMockServer.verify(1,
            getRequestedFor(urlPathEqualTo("/api/v1/check/crom/permission/urn:crom:foo"))
                .withQueryParam("action", equalTo("NONE,READ,WRITE,ADMIN")))

        assertIterableEquals(listOf(CromPermission.ADMIN, CromPermission.NONE).sorted(), user.permissions.sorted())
    }

    private inline fun <reified T> assertTypeEquals(result: Principal?): T {
        assertNotNull(result)
        assertEquals(T::class.java, result!!::class.java)
        return T::class.java.cast(result)
    }
}
