package io.ehdev.conrad.service.api.aop.impl;

import io.ehdev.conrad.service.api.aop.exception.PermissionDeniedException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tech.crom.model.security.authorization.AuthorizedObject;
import tech.crom.model.security.authorization.CromPermission;
import tech.crom.security.authorization.api.PermissionService;
import tech.crom.web.api.model.RequestDetails;

import static io.ehdev.conrad.service.api.aop.impl.RequestDetailsHelper.findRequestDetails;

@Aspect
@Service
public class PermissionRequiredCheck implements Ordered {

    private final Environment env;
    private final PermissionService permissionService;

    @Autowired
    public PermissionRequiredCheck(Environment env, PermissionService permissionService) {
        this.env = env;
        this.permissionService = permissionService;
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

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            throw new PermissionDeniedException("unknown user");
        }

        RequestDetails container = findRequestDetails(joinPoint);

        AuthorizedObject authorizedObject;
        if (container.getCromRepo() != null) {
            authorizedObject = container.getCromRepo();
        } else if (container.getCromProject() != null) {
            authorizedObject = container.getCromProject();
        } else {
            throw new RuntimeException("Unable to find project or repo to authenticate against");
        }

        if (!permissionService.hasAccessTo(authorizedObject, permission)) {
            throw new PermissionDeniedException(SecurityContextHolder.getContext().getAuthentication().getName());
        }
    }

    @Override
    public int getOrder() {
        return 1000;
    }
}
