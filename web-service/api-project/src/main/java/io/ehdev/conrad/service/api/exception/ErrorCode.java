package io.ehdev.conrad.service.api.exception;

public enum ErrorCode {
    REPO_DOES_NOT_EXIST("RP-001"),
    REPO_EXISTS_SHOULD_NOT("RP-002"),
    PROJECT_DOES_NOT_EXIST("PR-001"),
    PROJECT_EXISTS_SHOULD_NOT("PR-002"),
    ACCESS_REJECTED("PD-001"),
    LOGGED_IN_USER_REQUIRED("UR-001"),
    NON_API_USER_REQUIRED("UR-002"),
    UPDATE_MUST_BE_LONGER("UP-001"),
    USER_ALREADY_EXISTS("UP-002");

    private final String errorCode;

    ErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
