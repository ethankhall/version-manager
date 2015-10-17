package io.ehdev.conrad.app.service.repo.model;

import com.fasterxml.jackson.annotation.JsonView;
import io.ehdev.conrad.database.model.VcsRepoModel;

public class RepoResponseModel extends RepoCreateModel {

    @JsonView(ViewPermission.Public.class)
    private String id;

    @JsonView(ViewPermission.Private.class)
    private String token;

    public RepoResponseModel(String name, String url, String bumper, String id, String token) {
        super(name, url, bumper);
        this.id = id;
        this.token = token;
    }

    public RepoResponseModel() {
    }

    public RepoResponseModel(VcsRepoModel repo) {
        this(repo.getRepoName(), repo.getUrl(), repo.getVersionBumperName(), repo.getUuid(), repo.getToken());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
