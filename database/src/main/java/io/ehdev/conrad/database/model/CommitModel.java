package io.ehdev.conrad.database.model;

import io.ehdev.conrad.backend.version.commit.CommitVersion;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "repository_commit",
    uniqueConstraints = @UniqueConstraint(columnNames = {"commit_id", "vcs_repo"})
)
public class CommitModel implements UniqueModel, Comparable<CommitModel> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "commit_id", length = 40)
    String commitId;

    @Convert(converter = CommitVersionConverter.class)
    @Column(name = "version", length = 128)
    CommitVersion version;

    @CreatedDate
    @Column(name = "created_at")
    LocalDateTime createdAt;

    @JoinColumn(name = "vcs_repo")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    VcsRepoModel vcsRepoModel;

    @JoinColumn(name = "parent_commit")
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
    public long getId() {
        return id;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
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
            .append("id", id)
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
