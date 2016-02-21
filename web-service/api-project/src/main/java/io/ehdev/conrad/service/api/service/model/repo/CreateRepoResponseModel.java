package io.ehdev.conrad.service.api.service.model.repo;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

public class CreateRepoResponseModel extends ResourceSupport {
    @JsonProperty("projectName")
    String projectName;

    @JsonProperty("repoName")
    String repoName;

    @JsonProperty("url")
    String url;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
