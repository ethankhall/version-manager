package tech.crom.service.api.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Service
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import org.springframework.web.servlet.HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE
import tech.crom.database.api.ProjectManager
import tech.crom.database.api.RepoManager
import tech.crom.security.authorization.api.PermissionService
import tech.crom.web.api.model.RequestDetails
import javax.servlet.http.HttpServletRequest

@Service
class RequestDetailsParameterResolver @Autowired constructor(
    val projectManager: ProjectManager,
    val repoManager: RepoManager,
    val permissionService: PermissionService) : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean = parameter.parameterType == RequestDetails::class.java

    override fun resolveArgument(parameter: MethodParameter, mavContainer: ModelAndViewContainer, webRequest: NativeWebRequest, binderFactory: WebDataBinderFactory): RequestDetails {
        val httpServletRequest = webRequest.getNativeRequest(HttpServletRequest::class.java)
        return createRequestDetails(httpServletRequest)
    }

    fun createRequestDetails(httpServletRequest: HttpServletRequest): RequestDetails {
        val rawRequest = RequestDetails.RawRequestDetails(httpServletRequest.pathInfo, getParameters(httpServletRequest))
        var requestPermissions = RequestDetails.RequestPermissions(null, null)

        val projectName = rawRequest.getProjectName() ?: return RequestDetails(null, null, requestPermissions, rawRequest)
        val cromProject = projectManager.findProject(projectName) ?: return RequestDetails(null, null, requestPermissions, rawRequest)

        requestPermissions = requestPermissions.copy(projectPermission = permissionService.findHighestPermission(cromProject))
        val repoName = rawRequest.getRepoName() ?: return RequestDetails(cromProject, null, requestPermissions, rawRequest)
        val cromRepo = repoManager.findRepo(cromProject, repoName) ?: return RequestDetails(cromProject, null, requestPermissions, rawRequest)

        requestPermissions = requestPermissions.copy(repoPermission = permissionService.findHighestPermission(cromRepo))
        return RequestDetails(cromProject, cromRepo, requestPermissions, rawRequest)
    }

    @Suppress("UNCHECKED_CAST")
    private fun getParameters(webRequest: HttpServletRequest): Map<String, String> {
        return webRequest.getAttribute(URI_TEMPLATE_VARIABLES_ATTRIBUTE) as Map<String, String>
    }
}
