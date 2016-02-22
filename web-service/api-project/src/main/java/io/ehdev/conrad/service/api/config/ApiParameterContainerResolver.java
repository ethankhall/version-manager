package io.ehdev.conrad.service.api.config;

import io.ehdev.conrad.database.model.ApiParameterContainer;
import io.ehdev.conrad.database.model.permission.ApiTokenAuthentication;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
public class ApiParameterContainerResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(ApiParameterContainer.class);
    }

    @Override
    public ApiParameterContainer resolveArgument(MethodParameter parameter,
                                                 ModelAndViewContainer mavContainer,
                                                 NativeWebRequest webRequest,
                                                 WebDataBinderFactory binderFactory) throws Exception {

        ApiTokenAuthentication user = null;
        String projectName = null;
        String repoName = null;

        if (webRequest.getUserPrincipal() instanceof Authentication) {
            Object principal = ((Authentication) webRequest.getUserPrincipal()).getPrincipal();
            if (principal instanceof ApiTokenAuthentication) {
                user = (ApiTokenAuthentication) principal;
            }
        }

        Map<String, String> args = getParameters(webRequest);

        if(args.containsKey("projectName")) {
            projectName = args.get("projectName");
        }

        if(args.containsKey("repoName")) {
            repoName = args.get("repoName");
        }

        return new ApiParameterContainer(user, projectName, repoName);
    }

    @SuppressWarnings("unchecked")
    private Map<String, String> getParameters(NativeWebRequest webRequest) {
        HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        return (Map<String, String>) httpServletRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
    }
}
