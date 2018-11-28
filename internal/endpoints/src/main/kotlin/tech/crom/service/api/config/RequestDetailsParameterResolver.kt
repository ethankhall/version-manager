package tech.crom.service.api.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Service
import org.springframework.web.reactive.BindingContext
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import tech.crom.database.api.ProjectManager
import tech.crom.database.api.RepoManager
import tech.crom.model.project.CromProject
import tech.crom.model.repository.CromRepo
import tech.crom.model.security.authorization.CromPermission
import tech.crom.security.auth.ApiUser
import tech.crom.web.api.model.RequestDetails
import tech.crom.web.api.model.RequestPermissions
import java.security.Principal
import java.util.*

@Service
class RequestDetailsParameterResolver @Autowired constructor(
    val projectManager: ProjectManager,
    val repoManager: RepoManager) : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean = parameter.parameterType == RequestDetails::class.java

    override fun resolveArgument(parameter: MethodParameter, bindingContext: BindingContext, exchange: ServerWebExchange): Mono<Any> {
        val parameters = RequestDetails.RawRequestDetails(exchange.getAttributeOrDefault(RouterFunctions.URI_TEMPLATE_VARIABLES_ATTRIBUTE, emptyMap<String, String>()))
        val projectRepo = Mono
            .fromCallable { getProject(parameters) }
            .map { project -> (project to getRepo(parameters, project)) }

        return exchange.getPrincipal<Principal>()
            .map { permissionFromPrincipal(it) }
            .switchIfEmpty(Mono.just(DEFAULT_PERMISSIONS))
            .zipWith(projectRepo) { permissions, (project, repo) ->
                buildRequestDetails(permissions, project, repo, parameters)
            }
    }

    private fun permissionFromPrincipal(it: Principal?): List<CromPermission> {
        return when (it) {
            is ApiUser -> it.permissions
            else -> DEFAULT_PERMISSIONS
        }
    }

    private fun buildRequestDetails(permissions: List<CromPermission>, project: CromProject?, repo: CromRepo?, parameters: RequestDetails.RawRequestDetails): RequestDetails {
        val highestPermission = permissions.sortedWith(Comparator.comparingInt { it.permissionLevel }).lastOrNull()
            ?: CromPermission.READ
        return RequestDetails(project, repo, RequestPermissions(highestPermission, highestPermission, null), parameters)
    }


    fun getProject(parameters: RequestDetails.RawRequestDetails): CromProject? {
        val projectName = parameters.getProjectName() ?: return null
        return projectManager.findProject(projectName)
    }

    fun getRepo(parameters: RequestDetails.RawRequestDetails, project: CromProject?): CromRepo? {
        project ?: return null
        val repoName = parameters.getRepoName() ?: return null
        return repoManager.findRepo(project, repoName)
    }

    companion object {
        private val DEFAULT_PERMISSIONS = listOf(CromPermission.READ)
    }
}
