package io.ehdev.conrad.database.model;

import io.ehdev.conrad.database.model.permission.ApiTokenAuthentication;
import io.ehdev.conrad.database.model.project.ApiRepoModel;

public class ApiParameterContainer implements ApiRepoModel {
    private final ApiTokenAuthentication user;
    private final String projectName;
    private final String repoName;

    public ApiParameterContainer(ApiTokenAuthentication user, String projectName, String repoName) {
        this.user = user;
        this.projectName = projectName;
        this.repoName = repoName;
    }

    @Override
    public String getProjectName() {
        return projectName;
    }

    @Override
    public String getRepoName() {
        return repoName;
    }

    @Override
    public String getMergedName() {
        return projectName + "/" + repoName;
    }

    public ApiTokenAuthentication getUser() {
        return user;
    }
}
