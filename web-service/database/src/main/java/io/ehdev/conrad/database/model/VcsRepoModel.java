package io.ehdev.conrad.database.model;


import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "vcs_repo_data")
@Entity
public class VcsRepoModel implements UniqueModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "uuid", unique = true)
    @Type(type="pg-uuid")
    private UUID uuid;

    @Column(unique = true, name = "repo_name")
    String repoName;

    @Column(name = "url")
    String url;

    @Column(name = "repo_token", nullable = false, length = 60)
    String token;

    @JoinColumn(name = "version_bumper")
    @ManyToOne(optional = false)
    VersionBumperModel versionBumperModel;

    public VcsRepoModel(UUID uuid, String repoName, VersionBumperModel versionBumperModel) {
        this.uuid = uuid;
        this.repoName = repoName;
        this.versionBumperModel = versionBumperModel;
        this.token = RandomStringUtils.randomAlphanumeric(60);
    }

    public VcsRepoModel() {
    }

    @Override
    public long getId() {
        return id;
    }

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

    @Transient
    public UUID getUuidAsUUID() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Transient
    public String getVersionBumperName() {
        return versionBumperModel.getBumperName();
    }

    @Transient
    public String getVersionBumpterClassName() {
        return versionBumperModel.getClassName();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        VcsRepoModel that = (VcsRepoModel) o;

        return new EqualsBuilder()
            .append(id, that.id)
            .append(uuid, that.uuid)
            .append(repoName, that.repoName)
            .append(url, that.url)
            .append(token, that.token)
            .append(versionBumperModel, that.versionBumperModel)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(uuid)
            .append(repoName)
            .append(url)
            .append(token)
            .append(versionBumperModel)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("uuid", uuid)
            .append("repoName", repoName)
            .append("url", url)
            .append("token", token)
            .append("versionBumperModel", versionBumperModel)
            .toString();
    }
}
