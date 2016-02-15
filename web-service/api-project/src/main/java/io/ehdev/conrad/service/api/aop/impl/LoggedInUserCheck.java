package io.ehdev.conrad.service.api.aop.impl;

import io.ehdev.conrad.database.model.ApiParameterContainer;
import io.ehdev.conrad.service.api.aop.exception.UserNotLoggedInException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Service;

import static io.ehdev.conrad.service.api.aop.impl.ApiParameterHelper.findApiParameterContainer;

@Aspect
@Service
public class LoggedInUserCheck {

    @Before(value = "@annotation(io.ehdev.conrad.service.api.aop.annotation.LoggedInUserRequired)")
    public void verifyUserIsLoggedIn(JoinPoint joinPoint) {
        ApiParameterContainer container = findApiParameterContainer(joinPoint);

        if(null == container.getUser()) {
            throw new UserNotLoggedInException();
        }
    }
}
