package io.ehdev.conrad.database.api.exception;

public class ProjectAlreadyExistsException extends Exception{
    public ProjectAlreadyExistsException(String projectName) {
        super("Project " + projectName + " already defined");
    }
}
