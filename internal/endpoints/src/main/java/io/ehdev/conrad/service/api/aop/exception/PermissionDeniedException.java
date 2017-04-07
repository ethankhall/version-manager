package io.ehdev.conrad.service.api.aop.exception;

import io.ehdev.conrad.service.api.exception.BaseHttpException;
import io.ehdev.conrad.service.api.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class PermissionDeniedException extends BaseHttpException {

    public PermissionDeniedException(String name) {
        super(HttpStatus.UNAUTHORIZED, ErrorCode.ACCESS_REJECTED, name + " does not have access.");
    }
}
