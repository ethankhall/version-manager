package io.ehdev.conrad.service.api.aop.impl;

import io.ehdev.conrad.database.model.ApiParameterContainer;
import io.ehdev.conrad.service.api.aop.exception.UserNotLoggedInException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import static io.ehdev.conrad.service.api.aop.impl.ApiParameterHelper.findApiParameterContainer;

@Aspect
@Service
public class LoggedInUserCheck {

    private final Environment env;

    @Autowired
    public LoggedInUserCheck(Environment env) {
        this.env = env;
    }

    @Before(value = "@annotation(io.ehdev.conrad.service.api.aop.annotation.LoggedInUserRequired)")
    public void verifyUserIsLoggedIn(JoinPoint joinPoint) {
        if ("false".equalsIgnoreCase(env.getProperty("api.verification", "true"))) {
            return;
        }

        ApiParameterContainer container = findApiParameterContainer(joinPoint);

        if(null == container.getUser()) {
            throw new UserNotLoggedInException();
        }
    }
}
