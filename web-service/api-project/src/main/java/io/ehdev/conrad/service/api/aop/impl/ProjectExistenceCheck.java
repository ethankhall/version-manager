package io.ehdev.conrad.service.api.aop.impl;

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
import tech.crom.web.api.model.RequestDetails;

import static io.ehdev.conrad.service.api.aop.impl.RequestDetailsHelper.findRequestDetails;


@Aspect
@Service
public class ProjectExistenceCheck implements Ordered {

    private final Environment env;

    @Autowired
    public ProjectExistenceCheck(Environment env) {
        this.env = env;
    }

    @Before(value = "@annotation(projectRequired)", argNames = "joinPoint,projectRequired")
    public void verifyUserIsLoggedIn(JoinPoint joinPoint, ProjectRequired projectRequired) {
        if ("false".equalsIgnoreCase(env.getProperty("api.verification", "true"))) {
            return;
        }

        RequestDetails container = findRequestDetails(joinPoint);

        boolean projectExists = container.getCromProject() != null;
        if(!projectExists && projectRequired.exists()) {
            throw new ProjectMissingException(container.getRawRequest().getProjectName());
        } else if(projectExists && !projectRequired.exists()) {
            throw new ProjectExistsException(container.getRawRequest().getProjectName());
        }
    }

    @Override
    public int getOrder() {
        return -1000;
    }
}
