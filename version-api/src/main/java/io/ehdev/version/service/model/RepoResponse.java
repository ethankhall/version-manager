package io.ehdev.version.service.model;

import io.ehdev.version.commit.model.ScmMetaDataModel;

public class RepoResponse extends RepoCreation {
    String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public static RepoResponse fromMetaData(ScmMetaDataModel scmMetaDataModel) {
        RepoResponse repoResponse = new RepoResponse();
        repoResponse.setUuid(scmMetaDataModel.getUuid());
        repoResponse.setRepoName(scmMetaDataModel.getRepoName());
        repoResponse.setStrategyName(scmMetaDataModel.getVersionBumperName());
        return repoResponse;
    }
}
