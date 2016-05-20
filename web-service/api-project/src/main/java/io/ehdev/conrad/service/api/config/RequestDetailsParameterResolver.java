package io.ehdev.conrad.service.api.config;

import io.ehdev.conrad.database.api.PermissionManagementApi;
import io.ehdev.conrad.database.api.PrimaryKeySearchApi;
import io.ehdev.conrad.database.model.permission.ApiTokenAuthentication;
import io.ehdev.conrad.database.model.repo.RequestDetails;
import io.ehdev.conrad.database.model.repo.details.AuthUserDetails;
import io.ehdev.conrad.database.model.repo.details.ResourceDetails;
import io.ehdev.conrad.database.model.repo.details.ResourceId;
import io.ehdev.conrad.database.model.user.ApiUserPermission;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class RequestDetailsParameterResolver implements HandlerMethodArgumentResolver {

    private final PermissionManagementApi permissionManagementApi;
    private final PrimaryKeySearchApi primaryKeySearchApi;

    @Autowired
    public RequestDetailsParameterResolver(PermissionManagementApi permissionManagementApi, PrimaryKeySearchApi primaryKeySearchApi) {
        this.permissionManagementApi = permissionManagementApi;
        this.primaryKeySearchApi = primaryKeySearchApi;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(RequestDetails.class);
    }

    @Override
    public RequestDetails resolveArgument(MethodParameter parameter,
                                          ModelAndViewContainer mavContainer,
                                          NativeWebRequest webRequest,
                                          WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        return createRequestDetails(httpServletRequest);
    }

    @NotNull
    public RequestDetails createRequestDetails(HttpServletRequest webRequest) {
        Map<String, String> parameters = getParameters(webRequest);

        ResourceDetails resourceDetails = new ResourceDetails(getProjectResourceId(parameters), getRepoResourceId(parameters));
        AuthUserDetails authUserDetails = findAuthUserDetails(webRequest, resourceDetails);
        return new RequestDetails(authUserDetails, resourceDetails);
    }

    private ResourceId getProjectResourceId(Map<String, String> parameters) {
        if(parameters.containsKey("projectName")) {
            String projectName = parameters.get("projectName");
            Optional<UUID> projectId = primaryKeySearchApi.findProjectId(projectName);
            return new ResourceId(projectName, projectId.orElse(null));
        } else {
            return null;
        }
    }

    private ResourceId getRepoResourceId(Map<String, String> parameters) {
        if(parameters.containsKey("repoName") && parameters.containsKey("projectName")) {
            String repoName = parameters.get("repoName");
            String projectName = parameters.get("projectName");

            Optional<UUID> repoId = primaryKeySearchApi.findRepoId(projectName, repoName);
            return new ResourceId(projectName, repoId.orElse(null));
        } else {
            return null;
        }
    }

    private AuthUserDetails findAuthUserDetails(HttpServletRequest webRequest, ResourceDetails resourceDetails) {
        ApiTokenAuthentication apiTokenAuthentication = getApiTokenAuthentication(webRequest.getUserPrincipal());
        if(apiTokenAuthentication == null) {
            return null;

        }
        ApiUserPermission permission = permissionManagementApi.findHighestUserPermission(
            apiTokenAuthentication.getUuid(),
            resourceDetails);

        return new AuthUserDetails(
            apiTokenAuthentication.getUuid(),
            apiTokenAuthentication.getNiceName(),
            permission,
            apiTokenAuthentication);
    }

    private ApiTokenAuthentication getApiTokenAuthentication(Principal principal) {
        if (principal instanceof Authentication) {
            Object apiToken = ((Authentication) principal).getPrincipal();
            if (apiToken instanceof ApiTokenAuthentication) {
                return (ApiTokenAuthentication) apiToken;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private Map<String, String> getParameters(HttpServletRequest webRequest) {
        return (Map<String, String>) webRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
    }
}
