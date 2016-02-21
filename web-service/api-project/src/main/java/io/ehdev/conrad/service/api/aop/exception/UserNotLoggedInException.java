package io.ehdev.conrad.service.api.aop.exception;

import io.ehdev.conrad.service.api.exception.BaseHttpException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class UserNotLoggedInException extends BaseHttpException {
    public UserNotLoggedInException() {
        super(HttpStatus.FORBIDDEN, "UR-001", "User is not logged in.");
    }
}
