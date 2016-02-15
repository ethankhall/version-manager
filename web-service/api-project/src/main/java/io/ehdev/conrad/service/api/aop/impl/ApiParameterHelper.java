package io.ehdev.conrad.service.api.aop.impl;

import io.ehdev.conrad.database.model.ApiParameterContainer;
import org.aspectj.lang.JoinPoint;

public class ApiParameterHelper {

    private ApiParameterHelper() {
        //ignored
    }

    public static ApiParameterContainer findApiParameterContainer(JoinPoint joinPoint) {
        for (Object o : joinPoint.getArgs()) {
            if (o instanceof ApiParameterContainer) {
                return (ApiParameterContainer) o;
            }
        }
        throw new RuntimeException("Unable to find ApiParameterContainer on " + joinPoint.getSignature().toShortString());
    }
}
