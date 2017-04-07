package io.ehdev.conrad.service.api.aop.impl;

import io.ehdev.conrad.service.api.aop.annotation.RepoRequired;
import io.ehdev.conrad.service.api.aop.exception.RepositoryExistsException;
import io.ehdev.conrad.service.api.aop.exception.RepositoryMissingException;
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
public class RepoExistenceCheck implements Ordered {

    private final Environment env;

    @Autowired
    public RepoExistenceCheck(Environment env) {
        this.env = env;
    }

    @Before(value = "@annotation(repoRequired)", argNames = "joinPoint,repoRequired")
    public void verifyUserIsLoggedIn(JoinPoint joinPoint, RepoRequired repoRequired) {
        if ("false".equalsIgnoreCase(env.getProperty("api.verification", "true"))) {
            return;
        }

        RequestDetails container = findRequestDetails(joinPoint);

        boolean repoExists = container.getCromRepo() != null;
        boolean required = repoRequired.exists();

        if(required && !repoExists) {
            throw new RepositoryMissingException(container.getRawRequest().getProjectName(), container.getRawRequest().getRepoName());
        } else if(!required && repoExists) {
            throw new RepositoryExistsException(container.getRawRequest().getProjectName(), container.getRawRequest().getRepoName());
        }
    }

    @Override
    public int getOrder() {
        return -900;
    }
}
