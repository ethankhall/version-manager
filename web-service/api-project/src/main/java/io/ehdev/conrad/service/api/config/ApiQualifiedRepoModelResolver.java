package io.ehdev.conrad.service.api.config;

import io.ehdev.conrad.database.api.RepoManagementApi;
import io.ehdev.conrad.database.model.project.ApiQualifiedRepoModel;
import io.ehdev.conrad.service.api.exception.RepositoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
public class ApiQualifiedRepoModelResolver implements HandlerMethodArgumentResolver {

    private final RepoManagementApi repoManagementApi;

    @Autowired
    public ApiQualifiedRepoModelResolver(RepoManagementApi repoManagementApi) {
        this.repoManagementApi = repoManagementApi;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(ApiQualifiedRepoModel.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {

        Map<String, String> args = getParameters(webRequest);

        if(args.containsKey("repoName") && args.containsKey("projectName")) {
            String projectName = args.get("projectName");
            String repoName = args.get("repoName");
            ApiQualifiedRepoModel apiQualifiedRepoModel = new ApiQualifiedRepoModel(projectName, repoName);
            if(repoManagementApi.doesRepoExist(apiQualifiedRepoModel)) {
                return apiQualifiedRepoModel;
            } else {
                throw new RepositoryNotFoundException(projectName, repoName);
            }
        } else {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    private Map<String, String> getParameters(NativeWebRequest webRequest) {
        HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        return (Map<String, String>) httpServletRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
    }
}
