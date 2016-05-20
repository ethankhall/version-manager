package io.ehdev.conrad.service.api.aop.impl;

import io.ehdev.conrad.database.model.repo.RequestDetails;
import io.ehdev.conrad.service.api.aop.exception.NonUserNotAllowedException;
import io.ehdev.conrad.service.api.aop.exception.UserNotLoggedInException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import static io.ehdev.conrad.service.api.aop.impl.RequestDetailsHelper.findRequestDetails;

@Aspect
@Service
public class LoggedInUserCheck implements Ordered {

    private static final Logger logger = LoggerFactory.getLogger(LoggedInUserCheck.class);
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

        RequestDetails container = findRequestDetails(joinPoint);

        if(null == container.getAuthUserDetails()) {
            throw new UserNotLoggedInException();
        }

        logger.debug("Login check for {}, {}", container.getAuthUserDetails(), container.getClass().getSimpleName());
        if(container.getAuthUserDetails().isAuthenticationUser()) {
            throw new NonUserNotAllowedException();
        }
    }

    @Override
    public int getOrder() {
        return 900;
    }
}
