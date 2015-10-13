package io.ehdev.conrad.app.service.repo.model;

import io.ehdev.conrad.database.model.VcsRepoModel;

public class RepoResponseModel extends RepoCreateModel {
    private String id;

    public RepoResponseModel(String name, String url, String bumper, String id) {
        super(name, url, bumper);
        this.id = id;
    }

    public RepoResponseModel() {
    }

    public RepoResponseModel(VcsRepoModel repo) {
        this(repo.getRepoName(), repo.getUrl(), repo.getVersionBumperName(), repo.getUuid());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
