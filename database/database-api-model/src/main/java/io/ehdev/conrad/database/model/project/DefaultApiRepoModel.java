package io.ehdev.conrad.database.model.project;

public class DefaultApiRepoModel implements ApiFullRepoModel {

    private final String url;
    private final String projectName;
    private final String repoName;

    public DefaultApiRepoModel(String projectName, String repoName, String url) {
        this.url = url;
        this.projectName = projectName;
        this.repoName = repoName;
    }

    public DefaultApiRepoModel(String projectName, String repoName) {
        this(projectName, repoName, null);
    }

    @Override
    public String getUrl() {
        return url;
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
}
