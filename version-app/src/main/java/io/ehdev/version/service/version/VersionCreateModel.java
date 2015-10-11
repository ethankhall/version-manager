package io.ehdev.version.service.version;

import java.util.List;

public class VersionCreateModel extends VersionSearchModel {
    String message;

    public VersionCreateModel(List<String> commits, String message) {
        super(commits);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
