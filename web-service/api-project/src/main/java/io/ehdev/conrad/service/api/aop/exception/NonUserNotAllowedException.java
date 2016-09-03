package io.ehdev.conrad.service.api.aop.exception;

import io.ehdev.conrad.service.api.exception.BaseHttpException;
import org.springframework.http.HttpStatus;

public class NonUserNotAllowedException extends BaseHttpException {
    public NonUserNotAllowedException() {
        super(HttpStatus.I_AM_A_TEAPOT, "UR-002", "Real user required, not API user.");
    }
}
