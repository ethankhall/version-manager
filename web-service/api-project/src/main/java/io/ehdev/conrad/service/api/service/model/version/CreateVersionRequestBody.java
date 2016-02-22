package io.ehdev.conrad.service.api.service.model.version;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class CreateVersionRequestBody {

    @JsonProperty("commits")
    List<String> commits = new ArrayList<>();

    @NotNull
    @JsonProperty("message")
    String message;

    @NotNull
    @JsonProperty("commitId")
    String commitId;

    public CreateVersionRequestBody() {
    }

    public CreateVersionRequestBody(List<String> commits, String message, String commitId) {
        this.commits = commits;
        this.message = message;
        this.commitId = commitId;
    }

    public List<String> getCommits() {
        return commits;
    }

    public void setCommits(List<String> commits) {
        this.commits = commits;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCommitId() {
        return commitId;
    }

    public void setCommitId(String commitId) {
        this.commitId = commitId;
    }
}
