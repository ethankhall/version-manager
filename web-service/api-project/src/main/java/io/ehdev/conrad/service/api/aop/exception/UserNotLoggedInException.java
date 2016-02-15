package io.ehdev.conrad.service.api.aop.exception;

public class UserNotLoggedInException extends RuntimeException {
    public UserNotLoggedInException() {
        super("User is not logged in.");
    }
}
