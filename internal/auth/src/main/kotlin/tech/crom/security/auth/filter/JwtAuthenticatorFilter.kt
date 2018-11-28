package tech.crom.security.auth.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.cache.Cache
import com.google.common.cache.CacheBuilder
import org.apache.commons.lang3.StringUtils
import org.springframework.core.annotation.Order
import org.springframework.http.MediaType
import org.springframework.http.client.reactive.ClientHttpConnector
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import tech.crom.logger.getLogger
import tech.crom.model.security.authorization.CromPermission
import tech.crom.security.auth.AnonymousUser
import tech.crom.security.auth.ApiUser
import tech.crom.security.auth.CromPrincipal
import java.security.Principal
import java.time.Duration
import java.util.concurrent.TimeUnit

@Order(-10)
class JwtAuthenticatorFilter(accountManagerHost: String, private val om: ObjectMapper) : WebFilter {

    private val log by getLogger()

    private val cache: Cache<String, CromPrincipal> = CacheBuilder.newBuilder()
        .expireAfterWrite(1, TimeUnit.MINUTES)
        .maximumSize(1_000)
        .build()

    private var httpConnector: ClientHttpConnector = ReactorClientHttpConnector()
    private var webClient = WebClient.builder()
        .clientConnector(httpConnector)
        .baseUrl(accountManagerHost)
        .build()

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val credentials = findAuthToken(exchange)
        val attributes = exchange.getAttributeOrDefault(RouterFunctions.URI_TEMPLATE_VARIABLES_ATTRIBUTE, emptyMap<String, String>())

        val principal: Mono<Principal> = getPrincipal(credentials, attributes)

        val newExchange = exchange.mutate().principal(principal).build()
        return chain.filter(newExchange)
    }

    internal fun getPrincipal(credentials: String?, attributes: Map<String, String>): Mono<Principal> {
        return if (null != credentials) {
            val resolvedCreds = resolveCredentials(credentials)
            val permissions = resolvePermissions(credentials, attributes)

            resolvedCreds
                .zipWith(permissions) { cred, perm ->
                    when (cred) {
                        is ApiUser -> cred.copy(permissions = perm)
                        else -> AnonymousUser()
                    } as Principal
                }.switchIfEmpty(Mono.just(AnonymousUser()))
        } else {
            Mono.just(AnonymousUser())
        }
    }

    private fun resolvePermissions(credentials: String, attributes: Map<String, String>): Mono<List<CromPermission>> {
        val projectName = attributes["projectName"] ?: return Mono.just(emptyList())
        val repoName = attributes["repoName"]

        val urn = if (repoName != null) {
            "urn:$SERVICE_NAME:$projectName:$repoName"
        } else {
            "urn:$SERVICE_NAME:$projectName"
        }

        return webClient.get()
            .uri { builder ->
                builder
                    .path("/api/v1/check/$SERVICE_NAME/permission/$urn")
                    .queryParam("action", CromPermission.values().joinToString(",") { it.name })
                    .build()
            }.header(HeaderConst.AUTH_HEADER_NAME, credentials)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .timeout(Duration.ofSeconds(3))
            .retry(3)
            .doOnError {
                log.warn("There was an issue making a request to Account Manager!", it)
            }
            .flatMap { it -> handlePermissionResponse(it) }
    }

    private fun resolveCredentials(credentials: String): Mono<CromPrincipal> {
        val cacheValue = cache.getIfPresent(credentials)
        return if (cacheValue == null) {
            webClient.get().uri("/api/v1/user")
                .header(HeaderConst.AUTH_HEADER_NAME, credentials)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .timeout(Duration.ofSeconds(3))
                .retry(3)
                .doOnError {
                    log.warn("There was an issue making a request to Account Manager!", it)
                }
                .flatMap { it -> handleAuthResponse(credentials, it) }
        } else {
            Mono.just(cacheValue)
        }
    }

    private fun handlePermissionResponse(response: ClientResponse): Mono<List<CromPermission>> {
        when {
            response.statusCode().is2xxSuccessful -> {
                return response.bodyToMono(String::class.java)
                    .map { body ->
                        val json = om.readTree(body)
                        val permissionList = mutableListOf<CromPermission>()
                        for (permission in CromPermission.values()) {
                            if (json.has(permission.name) && json.get(permission.name).asBoolean(false)) {
                                permissionList.add(permission)
                            }
                        }
                        permissionList
                    }
            }
            response.statusCode().isError -> {
                return Mono.just(emptyList())
            }
            else -> {
                return Mono.just(emptyList())
            }
        }
    }

    private fun handleAuthResponse(credentials: String, response: ClientResponse): Mono<CromPrincipal> {
        when {
            response.statusCode().is2xxSuccessful -> {
                return response.bodyToMono(String::class.java)
                    .map { body ->
                        val json = om.readTree(body)
                        val userName = json.get("user")?.get("name")?.textValue() ?: return@map null
                        val accountManagerPrincipal = ApiUser(userName, emptyList())
                        cache.put(credentials, accountManagerPrincipal)
                        accountManagerPrincipal
                    }
            }
            response.statusCode().isError -> {
                log.warn("There was an issue making a request to Account Manager!", response)
                return Mono.just(AnonymousUser())
            }
            else -> {
                val principal = AnonymousUser()
                log.warn("User was not logged in.")
                return Mono.just(principal)
            }
        }
    }

    private fun findAuthToken(exchange: ServerWebExchange): String? {
        val request = exchange.request
        val cookieValue = request.cookies.getFirst(HeaderConst.COOKIE_NAME)?.value
        val headerValue = request.headers.getFirst(HeaderConst.AUTH_HEADER_NAME)

        return when {
            headerValue != null -> StringUtils.trimToNull(headerValue)
            cookieValue != null -> StringUtils.trimToNull(cookieValue)
            else -> null
        }
    }

    companion object {
        private const val SERVICE_NAME = "crom"
    }
}
