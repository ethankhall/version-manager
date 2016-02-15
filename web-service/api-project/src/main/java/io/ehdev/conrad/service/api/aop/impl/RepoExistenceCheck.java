package io.ehdev.conrad.service.api.aop.impl;

import io.ehdev.conrad.database.api.RepoManagementApi;
import io.ehdev.conrad.database.model.ApiParameterContainer;
import io.ehdev.conrad.service.api.aop.annotation.RepoRequired;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import static io.ehdev.conrad.service.api.aop.impl.ApiParameterHelper.findApiParameterContainer;

@Aspect
@Service
public class RepoExistenceCheck {

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

        ApiParameterContainer container = findApiParameterContainer(joinPoint);

        boolean repoExists = repoManagementApi.doesRepoExist(container);
        boolean required = repoRequired.exists();

        if(required && !repoExists) {
            throw new RuntimeException("Repo (" + container.getMergedName() + ") doesn't exist, but it is required.");
        } else if(!required && repoExists) {
            throw new RuntimeException("Repo (" + container.getMergedName() + ") exists, but it is required not to.");
        }
    }
}
