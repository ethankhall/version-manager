package io.ehdev.conrad.service.api.aop.exception;

import io.ehdev.conrad.service.api.exception.BaseHttpException;
import org.springframework.http.HttpStatus;

public class PermissionDeniedException extends BaseHttpException {

    public PermissionDeniedException(String name) {
        super(HttpStatus.UNAUTHORIZED, "PD-001", name + " does not have access.");
    }
}
