package io.ehdev.conrad.database.impl;

import io.ehdev.conrad.backend.version.commit.CommitVersion;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(
    name = "repository_commit",
    uniqueConstraints = @UniqueConstraint(columnNames = {"commit_id", "vcs_repo_uuid"})
)
public class CommitModel implements UniqueModel, Comparable<CommitModel> {

    @Id
    @Type(type="pg-uuid")
    @Column(name = "uuid", unique = true)
    @GeneratedValue(generator = "uuid-gen")
    @GenericGenerator(name = "uuid-gen", strategy = "uuid2")
    UUID id;

    @Column(name = "commit_id", length = 40)
    String commitId;

    @Convert(converter = CommitVersionConverter.class)
    @Column(name = "version", length = 128)
    CommitVersion version;

    @CreatedDate
    @Column(name = "created_at", columnDefinition= "timestamptz")
    ZonedDateTime createdAt;

    @JoinColumn(name = "vcs_repo_uuid")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    VcsRepoModel vcsRepoModel;

    @JoinColumn(name = "parent_commit_uuid")
    @OneToOne(fetch = FetchType.LAZY)
    CommitModel parentCommit;

    public CommitModel(String commitId,
                       VcsRepoModel vcsRepoModel,
                       CommitVersion version,
                       CommitModel parent) {
        this.commitId = commitId;
        this.version = version;
        this.vcsRepoModel = vcsRepoModel;
        this.parentCommit = parent;
    }

    public CommitModel() {
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID uuid) {
        this.id = uuid;
    }

    public String getCommitId() {
        return commitId;
    }

    public void setCommitId(String commitId) {
        this.commitId = commitId;
    }

    public CommitVersion getVersion() {
        return version;
    }

    public void setVersion(CommitVersion version) {
        this.version = version;
    }

    public VcsRepoModel getVcsRepoModel() {
        return vcsRepoModel;
    }

    public void setVcsRepoModel(VcsRepoModel vcsRepoModel) {
        this.vcsRepoModel = vcsRepoModel;
    }

    public CommitModel getParentCommit() {
        return parentCommit;
    }

    public void setParentCommit(CommitModel parentCommit) {
        this.parentCommit = parentCommit;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = ZonedDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        CommitModel that = (CommitModel) o;

        return new EqualsBuilder()
            .append(id, that.id)
            .append(commitId, that.commitId)
            .append(version, that.version)
            .append(createdAt, that.createdAt)
            .append(vcsRepoModel, that.vcsRepoModel)
            .append(parentCommit, that.parentCommit)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(commitId)
            .append(version)
            .append(createdAt)
            .append(vcsRepoModel)
            .append(parentCommit)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("uuid", id)
            .append("commitId", commitId)
            .append("version", version)
            .append("createdAt", createdAt)
            .append("vcsRepoModel", vcsRepoModel)
            .append("parentCommit", parentCommit)
            .toString();
    }

    @Override
    public int compareTo(CommitModel o) {
        return getVersion().compareTo(o.getVersion());
    }
}
