package io.ehdev.conrad.service.api.aop.impl;

import io.ehdev.conrad.service.api.aop.exception.PermissionDeniedException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import tech.crom.model.security.authorization.CromPermission;
import tech.crom.web.api.model.RequestDetails;

import static io.ehdev.conrad.service.api.aop.impl.RequestDetailsHelper.findRequestDetails;

@Aspect
@Service
public class PermissionRequiredCheck implements Ordered {

    private final Environment env;

    @Autowired
    public PermissionRequiredCheck(Environment env) {
        this.env = env;
    }

    @Before(value = "@annotation(io.ehdev.conrad.service.api.aop.annotation.ReadPermissionRequired)")
    public void readPermissionRequired(JoinPoint joinPoint) {
        checkPermission(joinPoint, CromPermission.READ);
    }

    @Before(value = "@annotation(io.ehdev.conrad.service.api.aop.annotation.WritePermissionRequired)")
    public void writePermissionRequired(JoinPoint joinPoint) {
        checkPermission(joinPoint, CromPermission.WRITE);
    }

    @Before(value = "@annotation(io.ehdev.conrad.service.api.aop.annotation.AdminPermissionRequired)")
    public void adminPermissionRequired(JoinPoint joinPoint) {
        checkPermission(joinPoint, CromPermission.ADMIN);
    }

    private void checkPermission(JoinPoint joinPoint, CromPermission permission) {
        if (!env.getProperty("api.verification", Boolean.class, true)) {
            return;
        }

        RequestDetails container = findRequestDetails(joinPoint);

        CromPermission highestPermission = container.getRequestPermission().findHighestPermission();

        if (!highestPermission.isHigherOrEqualThan(permission)) {
            throw new PermissionDeniedException(container.getRequestPermission().findUserName());
        }
    }

    @Override
    public int getOrder() {
        return 1000;
    }
}
