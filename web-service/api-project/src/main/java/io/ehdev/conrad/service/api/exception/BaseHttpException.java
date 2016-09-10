package io.ehdev.conrad.service.api.exception;

import org.springframework.http.HttpStatus;

import java.time.Clock;

public class BaseHttpException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final ErrorCode errorCode;
    private final long timestamp;

    public BaseHttpException(HttpStatus httpStatus, ErrorCode errorCode, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.timestamp = Clock.systemUTC().millis();
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getErrorCode() {
        return errorCode.getErrorCode();
    }

    public long getTimestamp() {
        return timestamp;
    }
}
