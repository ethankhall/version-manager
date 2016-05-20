package io.ehdev.conrad.service.api.aop.impl;

import io.ehdev.conrad.database.api.RepoManagementApi;
import io.ehdev.conrad.database.model.repo.RequestDetails;
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

import static io.ehdev.conrad.service.api.aop.impl.RequestDetailsHelper.findRequestDetails;

@Aspect
@Service
public class RepoExistenceCheck implements Ordered {

    private final Environment env;
    private final RepoManagementApi repoManagementApi;

    @Autowired
    public RepoExistenceCheck(Environment env, RepoManagementApi repoManagementApi) {
        this.env = env;
        this.repoManagementApi = repoManagementApi;
    }

    @Before(value = "@annotation(repoRequired)", argNames = "joinPoint,repoRequired")
    public void verifyUserIsLoggedIn(JoinPoint joinPoint, RepoRequired repoRequired) {
        if ("false".equalsIgnoreCase(env.getProperty("api.verification", "true"))) {
            return;
        }

        RequestDetails container = findRequestDetails(joinPoint);

        boolean repoExists = repoManagementApi.doesRepoExist(container.getResourceDetails());
        boolean required = repoRequired.exists();

        if(required && !repoExists) {
            throw new RepositoryMissingException(container.getResourceDetails());
        } else if(!required && repoExists) {
            throw new RepositoryExistsException(container.getResourceDetails());
        }
    }

    @Override
    public int getOrder() {
        return -900;
    }
}
