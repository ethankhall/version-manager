package io.ehdev.conrad.service.api.service.model.repo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.ehdev.conrad.model.rest.repo.RestUserPermissionModel;
import org.springframework.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GetRepoModel extends ResourceSupport {
    @JsonProperty("projectName")
    String projectName;

    @JsonProperty("repoName")
    String repoName;

    @JsonProperty("url")
    String url;

    @JsonProperty("permissions")
    List<RestUserPermissionModel> permissions = new ArrayList<>();

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