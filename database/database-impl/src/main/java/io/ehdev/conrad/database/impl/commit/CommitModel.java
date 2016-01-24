package io.ehdev.conrad.database.impl.commit;

import io.ehdev.conrad.database.impl.repo.RepoModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(
    name = "commit_model",
    uniqueConstraints = @UniqueConstraint(columnNames = {"commit_id", "repo_uuid"})
)
public class CommitModel implements Comparable<CommitModel> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, unique = true, name = "uuid")
    UUID id;

    @Column(name = "commit_id", length = 40)
    String commitId;

    @Column(name = "version", length = 128)
    String version;

    @CreatedDate
    @Column(name = "created_at")
    ZonedDateTime createdAt;

    @JoinColumn(name = "repo_uuid")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    RepoModel repoModel;

    @JoinColumn(name = "parent_commit_uuid")
    @OneToOne(fetch = FetchType.LAZY)
    CommitModel parentCommit;

    public CommitModel(String commitId,
                       RepoModel repoModel,
                       String version,
                       CommitModel parent) {
        this.commitId = commitId;
        this.version = version;
        this.repoModel = repoModel;
        this.parentCommit = parent;
    }

    public CommitModel() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID uuid) {
        this.id = uuid;
    }

    public String getCommitId() {
        return commitId;
    }

    public void setCommitId(String commitId) {
        this.commitId = commitId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public RepoModel getRepoModel() {
        return repoModel;
    }

    public void setRepoModel(RepoModel repoModel) {
        this.repoModel = repoModel;
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
            .append(repoModel, that.repoModel)
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
            .append(repoModel)
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
            .append("repoModel", repoModel)
            .append("parentCommit", parentCommit)
            .toString();
    }

    @Override
    public int compareTo(CommitModel o) {
        return getCreatedAt().compareTo(o.getCreatedAt());
    }
}
