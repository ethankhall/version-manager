package io.ehdev.conrad.service.api.aop.impl;

import io.ehdev.conrad.database.api.PermissionManagementApi;
import io.ehdev.conrad.database.model.ApiParameterContainer;
import io.ehdev.conrad.database.model.permission.ApiTokenAuthentication;
import io.ehdev.conrad.database.model.user.ApiUserPermission;
import io.ehdev.conrad.service.api.aop.exception.PermissionDeniedException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import static io.ehdev.conrad.service.api.aop.impl.ApiParameterHelper.findApiParameterContainer;

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

        ApiParameterContainer container = findApiParameterContainer(joinPoint);

        ApiTokenAuthentication user = container.getUser();

        boolean granted = permissionManagementApi.doesUserHavePermission(user,
            container.getProjectName(),
            container.getRepoName(),
            permission);

        if (!granted) {
            if (user == null) {
                throw new PermissionDeniedException("unknown");
            } else {
                throw new PermissionDeniedException(user.getNiceName());
            }
        }
    }

    @Override
    public int getOrder() {
        return 1000;
    }
}
