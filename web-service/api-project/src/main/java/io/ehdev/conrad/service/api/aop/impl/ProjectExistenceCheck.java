package io.ehdev.conrad.service.api.aop.impl;

import io.ehdev.conrad.database.model.repo.RequestDetails;
import io.ehdev.conrad.service.api.aop.annotation.ProjectRequired;
import io.ehdev.conrad.service.api.aop.exception.ProjectExistsException;
import io.ehdev.conrad.service.api.aop.exception.ProjectMissingException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import tech.crom.business.api.ProjectApi;

import static io.ehdev.conrad.service.api.aop.impl.RequestDetailsHelper.findRequestDetails;


@Aspect
@Service
public class ProjectExistenceCheck implements Ordered {

    private final Environment env;
    private final ProjectApi projectApi;

    @Autowired
    public ProjectExistenceCheck(Environment env, ProjectApi projectApi) {
        this.env = env;
        this.projectApi = projectApi;
    }

    @Before(value = "@annotation(projectRequired)", argNames = "joinPoint,projectRequired")
    public void verifyUserIsLoggedIn(JoinPoint joinPoint, ProjectRequired projectRequired) {
        if ("false".equalsIgnoreCase(env.getProperty("api.verification", "true"))) {
            return;
        }

        RequestDetails container = findRequestDetails(joinPoint);

        boolean projectExists = projectApi.projectExists(container.getResourceDetails().getProjectId().getName());
        if(!projectExists && projectRequired.exists()) {
            throw new ProjectMissingException(container.getResourceDetails());
        } else if(projectExists && !projectRequired.exists()) {
            throw new ProjectExistsException(container.getResourceDetails());
        }
    }

    @Override
    public int getOrder() {
        return -1000;
    }
}
