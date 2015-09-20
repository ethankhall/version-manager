package io.ehdev.version.commit.model;


import io.ehdev.version.commit.ScmMetaData;

import javax.persistence.*;

@Entity
public class ScmMetaDataModel implements ScmMetaData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    String repoName;

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    VersionBumperModel versionBumperModel;


    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public void setVersionBumperModel(VersionBumperModel versionBumperModel) {
        this.versionBumperModel = versionBumperModel;
    }

    @Override
    @Transient
    public String getVersionBumperName() {
        return versionBumperModel.getBumperName();
    }

}
