package io.ehdev.conrad.service.api.aop;

import io.ehdev.conrad.database.api.PermissionManagementApi;
import io.ehdev.conrad.database.model.ApiParameterContainer;
import io.ehdev.conrad.database.model.user.ApiUserPermission;
import io.ehdev.conrad.service.api.aop.exception.PermissionDeniedException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Aspect
@Service
public class PermissionRequiredCheck {

    private final PermissionManagementApi permissionManagementApi;

    @Autowired
    public PermissionRequiredCheck(PermissionManagementApi permissionManagementApi) {
        this.permissionManagementApi = permissionManagementApi;
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

    private void checkPermission(JoinPoint joinPoint, ApiUserPermission read) {
        ApiParameterContainer container = null;
        for (Object o : joinPoint.getArgs()) {
            if (o instanceof ApiParameterContainer) {
                container = (ApiParameterContainer) o;
                break;
            }
        }

        if (container == null) {
            throw new RuntimeException("Unable to find ApiParameterContainer on " + joinPoint.getSignature().toShortString());
        }

        boolean permission = permissionManagementApi.doesUserHavePermission(container.getUser(), container.getProjectName(), container.getRepoName(), read);
        if (!permission) {
            throw new PermissionDeniedException();
        }
    }
}
