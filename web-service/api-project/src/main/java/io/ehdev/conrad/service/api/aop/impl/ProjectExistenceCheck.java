package io.ehdev.conrad.service.api.aop.impl;

import io.ehdev.conrad.database.api.ProjectManagementApi;
import io.ehdev.conrad.database.model.project.ApiProjectDetails;
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

import java.util.Optional;

import static io.ehdev.conrad.service.api.aop.impl.RequestDetailsHelper.findRequestDetails;


@Aspect
@Service
public class ProjectExistenceCheck implements Ordered {

    private final Environment env;
    private final ProjectManagementApi projectManagementApi;

    @Autowired
    public ProjectExistenceCheck(Environment env, ProjectManagementApi projectManagementApi) {
        this.env = env;
        this.projectManagementApi = projectManagementApi;
    }

    @Before(value = "@annotation(projectRequired)", argNames = "joinPoint,projectRequired")
    public void verifyUserIsLoggedIn(JoinPoint joinPoint, ProjectRequired projectRequired) {
        if ("false".equalsIgnoreCase(env.getProperty("api.verification", "true"))) {
            return;
        }

        RequestDetails container = findRequestDetails(joinPoint);
        Optional<ApiProjectDetails> projectDetails = projectManagementApi.getProjectDetails(container.getResourceDetails());

        if(!projectDetails.isPresent() && projectRequired.exists()) {
            throw new ProjectMissingException(container.getResourceDetails());
        } else if(projectDetails.isPresent() && !projectRequired.exists()) {
            throw new ProjectExistsException(container.getResourceDetails());
        }
    }

    @Override
    public int getOrder() {
        return -1000;
    }
}
