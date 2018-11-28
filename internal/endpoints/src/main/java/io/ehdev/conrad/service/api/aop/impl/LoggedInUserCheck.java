package io.ehdev.conrad.service.api.aop.impl;

import io.ehdev.conrad.service.api.aop.exception.NonUserNotAllowedException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import tech.crom.web.api.model.RequestDetails;

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

        if(container.getRequestPermission().getCromUser() == null) {
            logger.info("Not authorized user, forbidden from accessing private apis.");
            throw new NonUserNotAllowedException();
        }
    }

    @Override
    public int getOrder() {
        return 900;
    }
}
