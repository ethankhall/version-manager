package io.ehdev.conrad.service.api.aop.impl;

import io.ehdev.conrad.database.model.repo.RequestDetails;
import org.aspectj.lang.JoinPoint;

public class RequestDetailsHelper {

    private RequestDetailsHelper() {
        //ignored
    }

    public static RequestDetails findRequestDetails(JoinPoint joinPoint) {
        for (Object o : joinPoint.getArgs()) {
            if (o instanceof RequestDetails) {
                return (RequestDetails) o;
            }
        }
        throw new RuntimeException("Unable to find RequestDetails on " + joinPoint.getSignature().toShortString());
    }
}
