package io.ehdev.version.model.commit.model;


import io.ehdev.version.model.commit.ScmMetaData;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "scm_meta_data")
@Entity
public class ScmMetaDataModel implements ScmMetaData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "uuid", unique = true)
    @Type(type="pg-uuid")
    private UUID uuid;

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
        return uuid.toString();
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    @Transient
    public String getVersionBumperName() {
        return versionBumperModel.getBumperName();
    }

}
