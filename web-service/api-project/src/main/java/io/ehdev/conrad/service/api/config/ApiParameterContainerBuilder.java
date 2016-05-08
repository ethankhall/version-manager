package io.ehdev.conrad.service.api.config;

import io.ehdev.conrad.database.model.ApiParameterContainer;
import io.ehdev.conrad.database.model.permission.ApiTokenAuthentication;
import org.springframework.security.core.Authentication;

import java.security.Principal;
import java.util.Map;

public class ApiParameterContainerBuilder {

    private final Principal principal;
    private final Map<String, String> parameters;

    public ApiParameterContainerBuilder(Principal principal, Map<String, String> parameters) {
        this.principal = principal;
        this.parameters = parameters;
    }

    public ApiParameterContainer createContainer() {

        ApiTokenAuthentication user = null;
        String projectName = null;
        String repoName = null;

        if (principal instanceof Authentication) {
            Object apiToken = ((Authentication) principal).getPrincipal();
            if (apiToken instanceof ApiTokenAuthentication) {
                user = (ApiTokenAuthentication) apiToken;
            }
        }

        if(parameters.containsKey("projectName")) {
            projectName = parameters.get("projectName");
        }

        if(parameters.containsKey("repoName")) {
            repoName = parameters.get("repoName");
        }

        return new ApiParameterContainer(user, projectName, repoName);
    }
}
