package io.ehdev.conrad.database.api.exception;

public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException(String projectName) {
        super(String.format("Project %s is not valid", projectName));
    }
}
