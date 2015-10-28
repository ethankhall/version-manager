package io.ehdev.conrad.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_ACCEPTABLE, reason="Version conflict, version is already defined.")
public class VersionConflictException extends RuntimeException {
    public VersionConflictException(String s) {
        super(s);
    }
}
