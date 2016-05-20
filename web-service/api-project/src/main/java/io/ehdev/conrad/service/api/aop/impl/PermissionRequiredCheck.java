package io.ehdev.conrad.service.api.aop.impl;

import io.ehdev.conrad.database.api.PermissionManagementApi;
import io.ehdev.conrad.database.model.repo.RequestDetails;
import io.ehdev.conrad.database.model.user.ApiUserPermission;
import io.ehdev.conrad.service.api.aop.exception.PermissionDeniedException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import static io.ehdev.conrad.service.api.aop.impl.RequestDetailsHelper.findRequestDetails;

@Aspect
@Service
public class PermissionRequiredCheck implements Ordered {

    private final PermissionManagementApi permissionManagementApi;
    private final Environment env;

    @Autowired
    public PermissionRequiredCheck(PermissionManagementApi permissionManagementApi, Environment env) {
        this.permissionManagementApi = permissionManagementApi;
        this.env = env;
    }

    @Before(value = "@annotation(io.ehdev.conrad.service.api.aop.annotation.ReadPermissionRequired)")
    public void readPermissionRequired(JoinPoint joinPoint) {
        checkPermission(joinPoint, ApiUserPermission.READ);
    }

    @Before(value = "@annotation(io.ehdev.conrad.service.api.aop.annotation.WritePermissionRequired)")
    public void writePermissionRequired(JoinPoint joinPoint) {
        checkPermission(joinPoint, ApiUserPermission.WRITE);
    }

    @Before(value = "@annotation(io.ehdev.conrad.service.api.aop.annotation.AdminPermissionRequired)")
    public void adminPermissionRequired(JoinPoint joinPoint) {
        checkPermission(joinPoint, ApiUserPermission.ADMIN);
    }

    private void checkPermission(JoinPoint joinPoint, ApiUserPermission permission) {
        if (!env.getProperty("api.verification", Boolean.class, true)) {
            return;
        }

        RequestDetails container = findRequestDetails(joinPoint);

        if(container.getAuthUserDetails() == null) {
            throw new PermissionDeniedException("unknown");
        }

        if (!container.getAuthUserDetails().getPermission().isHigherOrEqualTo(permission)) {
            throw new PermissionDeniedException(container.getAuthUserDetails().getName());
        }
    }

    @Override
    public int getOrder() {
        return 1000;
    }
}
