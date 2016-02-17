package io.ehdev.conrad.model.rest.repo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RestRepoModel {

    @JsonProperty("projectName")
    String projectName;

    @JsonProperty("repoName")
    String repoName;

    @JsonProperty("url")
    String url;

    @JsonProperty("permissions")
    List<RestUserPermissionModel> permissions = new ArrayList<>();

    public RestRepoModel(String projectName, String repoName, String url) {
        this.projectName = projectName;
        this.repoName = repoName;
        this.url = url;
    }

    public RestRepoModel(String projectName, String repoName) {
        this(projectName, repoName, null);
    }

    public RestRepoModel() {}

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

    public List<RestUserPermissionModel> getPermissions() {
        return Collections.unmodifiableList(permissions);
    }

    public void addPermission(RestUserPermissionModel permissions) {
        this.permissions.add(permissions);
    }
}
