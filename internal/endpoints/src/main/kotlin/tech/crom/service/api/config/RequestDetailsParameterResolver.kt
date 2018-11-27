package tech.crom.service.api.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Service
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import org.springframework.web.servlet.HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE
import tech.crom.business.api.PermissionApi
import tech.crom.database.api.ProjectManager
import tech.crom.database.api.RepoManager
import tech.crom.model.project.CromProject
import tech.crom.model.repository.CromRepo
import tech.crom.model.security.authorization.CromPermission
import tech.crom.model.user.CromUser
import tech.crom.web.api.model.RequestDetails
import tech.crom.web.api.model.RequestPermissions
import javax.servlet.http.HttpServletRequest

@Service
class RequestDetailsParameterResolver @Autowired constructor(
    val projectManager: ProjectManager,
    val repoManager: RepoManager,
    val permissionApi: PermissionApi) : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean = parameter.parameterType == RequestDetails::class.java

    override fun resolveArgument(parameter: MethodParameter, mavContainer: ModelAndViewContainer, webRequest: NativeWebRequest, binderFactory: WebDataBinderFactory): RequestDetails {
        val httpServletRequest = webRequest.getNativeRequest(ServletWebRequest::class.java)
        return createRequestDetails(httpServletRequest)
    }

    fun createRequestDetails(httpServletRequest: ServletWebRequest): RequestDetails {
        val rawRequest = httpServletRequest.createRawRequestDetails()

        val project = getProject(rawRequest)
        val repo = getRepo(rawRequest, project)

        val authentication = SecurityContextHolder.getContext().authentication
        val permission = when (authentication) {
            is CromRepositoryAuthentication -> getPermissionsForRepoAuth(repo, authentication)
            is CromUserAuthentication -> getPermissionForUserAuth(project, repo, authentication)
            else -> createNoPermissions()
        }

        return RequestDetails(project, repo, permission, rawRequest)
    }

    fun createNoPermissions(): RequestPermissions {
        return RequestPermissions(CromPermission.READ, CromPermission.READ, null)
    }

    fun getPermissionForUserAuth(project: CromProject?, repo: CromRepo?, auth: CromUserAuthentication): RequestPermissions {
        val projectPermission = if (project != null) permissionApi.findHighestPermission(project) else CromPermission.READ
        val repoPermission = if (repo != null) permissionApi.findHighestPermission(repo) else CromPermission.READ
        return RequestPermissions(projectPermission, repoPermission, auth.user)
    }

    fun getPermissionsForRepoAuth(repo: CromRepo?, auth: CromRepositoryAuthentication): RequestPermissions {
        if (repo != null) {
            if (repo.projectId == auth.source.projectId && repo.repoId == auth.source.repoId) {
                return RequestPermissions(CromPermission.NONE, CromPermission.WRITE, CromUser.REPO_USER)
            }
        }

        return createNoPermissions()
    }

    fun getProject(request: RequestDetails.RawRequestDetails): CromProject? {
        val projectName = request.getProjectName() ?: return null
        return projectManager.findProject(projectName)
    }

    fun getRepo(request: RequestDetails.RawRequestDetails, project: CromProject?): CromRepo? {
        project ?: return null
        val repoName = request.getRepoName() ?: return null
        return repoManager.findRepo(project, repoName)
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        private fun getParameters(webRequest: ServletWebRequest): Map<String, String> {
            return webRequest.request.getAttribute(URI_TEMPLATE_VARIABLES_ATTRIBUTE) as Map<String, String>
        }
    }

    fun ServletWebRequest.createRawRequestDetails(): RequestDetails.RawRequestDetails {
        val path = this.request.requestURI.substring(this.contextPath.length)
        val headers = mutableMapOf<String, String>()
        this.request.headerNames.toList().forEach {
            headers[it] = this.getHeader(it)
        }

        return RequestDetails.RawRequestDetails(path, this.request.method, headers, getParameters(this))
    }
}
