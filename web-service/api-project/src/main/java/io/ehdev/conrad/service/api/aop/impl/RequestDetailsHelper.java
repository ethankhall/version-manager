package io.ehdev.conrad.service.api.aop.impl;

import org.aspectj.lang.JoinPoint;
import tech.crom.web.api.model.RequestDetails;

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
