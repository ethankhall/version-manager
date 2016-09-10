package io.ehdev.conrad.service.api.aop.exception;

import io.ehdev.conrad.service.api.exception.BaseHttpException;
import io.ehdev.conrad.service.api.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class NonUserNotAllowedException extends BaseHttpException {
    public NonUserNotAllowedException() {
        super(HttpStatus.I_AM_A_TEAPOT, ErrorCode.NON_API_USER_REQUIRED, "Real user required, not API user.");
    }
}
