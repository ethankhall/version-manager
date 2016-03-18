package io.ehdev.conrad.service.api.aop.impl;

import io.ehdev.conrad.database.api.ProjectManagementApi;
import io.ehdev.conrad.database.api.RepoManagementApi;
import io.ehdev.conrad.database.model.ApiParameterContainer;
import io.ehdev.conrad.database.model.project.ApiProjectDetails;
import io.ehdev.conrad.service.api.aop.annotation.ProjectRequired;
import io.ehdev.conrad.service.api.aop.annotation.RepoRequired;
import io.ehdev.conrad.service.api.aop.exception.ProjectExistsException;
import io.ehdev.conrad.service.api.aop.exception.ProjectMissingException;
import io.ehdev.conrad.service.api.aop.exception.RepositoryExistsException;
import io.ehdev.conrad.service.api.aop.exception.RepositoryMissingException;
import java.util.Optional;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import static io.ehdev.conrad.service.api.aop.impl.ApiParameterHelper.findApiParameterContainer;


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

        ApiParameterContainer container = findApiParameterContainer(joinPoint);
        Optional<ApiProjectDetails> projectDetails = projectManagementApi.getProjectDetails(container);

        if(!projectDetails.isPresent() && projectRequired.exists()) {
            throw new ProjectMissingException(container);
        } else if(projectDetails.isPresent() && !projectRequired.exists()) {
            throw new ProjectExistsException(container);
        }
    }

    @Override
    public int getOrder() {
        return -1000;
    }
}
