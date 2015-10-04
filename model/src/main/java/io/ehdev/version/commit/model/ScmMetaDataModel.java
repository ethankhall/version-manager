package io.ehdev.version.commit.model;


import io.ehdev.version.commit.ScmMetaData;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Table(name = "scm_meta_data")
@Entity
public class ScmMetaDataModel implements ScmMetaData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "uuid", unique = true)
    private String uuid;

    @Column(unique = true, name = "repo_name")
    String repoName;

    @JoinColumn(name = "version_bumper")
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    @Transient
    public String getVersionBumperName() {
        return versionBumperModel.getClassName();
    }

}
