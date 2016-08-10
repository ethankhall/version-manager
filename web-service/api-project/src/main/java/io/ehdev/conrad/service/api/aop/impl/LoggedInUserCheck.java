package io.ehdev.conrad.service.api.aop.impl;

import io.ehdev.conrad.service.api.aop.exception.NonUserNotAllowedException;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tech.crom.model.security.authentication.CromRepositoryAuthentication;

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
    public void verifyUserIsLoggedIn() {
        if ("false".equalsIgnoreCase(env.getProperty("api.verification", "true"))) {
            return;
        }

        if(SecurityContextHolder.getContext().getAuthentication() instanceof CromRepositoryAuthentication) {
            logger.info("User is api user, forbidden from accessing private apis: {}", SecurityContextHolder.getContext().getAuthentication());
            throw new NonUserNotAllowedException();
        }
    }

    @Override
    public int getOrder() {
        return 900;
    }
}
