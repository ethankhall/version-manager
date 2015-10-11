package io.ehdev.version.model.commit.model;

import io.ehdev.version.model.commit.CommitVersion;
import io.ehdev.version.model.commit.RepositoryCommit;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(
    name = "repository_commit",
    uniqueConstraints = @UniqueConstraint(columnNames = {"commit_id", "scm_meta_data"})
)
@Entity
public class RepositoryCommitModel implements RepositoryCommit {

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

    @JoinColumn(name = "scm_meta_data")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    ScmMetaDataModel scmMetaDataModel;

    @JoinColumn(name = "next_commit")
    @OneToOne(fetch = FetchType.LAZY)
    RepositoryCommitModel nextCommit;

    @JoinColumn(name = "bugfix_commit")
    @OneToOne(fetch = FetchType.LAZY)
    RepositoryCommitModel bugfixCommit;

    public RepositoryCommitModel(String commitId,
                                 CommitVersion version,
                                 RepositoryCommitModel nextCommit,
                                 RepositoryCommitModel bugfixCommit) {
        this.commitId = commitId;
        this.version = version;
        this.nextCommit = nextCommit;
        this.bugfixCommit = bugfixCommit;
    }

    public RepositoryCommitModel(String commitId, CommitVersion version) {
        this(commitId, version, null, null);
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

    public ScmMetaDataModel getScmMetaDataModel() {
        return scmMetaDataModel;
    }

    public void setScmMetaDataModel(ScmMetaDataModel scmMetaDataModel) {
        this.scmMetaDataModel = scmMetaDataModel;
    }

    public RepositoryCommitModel getNextCommit() {
        return nextCommit;
    }

    public void setNextCommit(RepositoryCommitModel nextCommit) {
        this.nextCommit = nextCommit;
    }

    public RepositoryCommitModel getBugfixCommit() {
        return bugfixCommit;
    }

    public void setBugfixCommit(RepositoryCommitModel bugfixCommit) {
        this.bugfixCommit = bugfixCommit;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        RepositoryCommitModel that = (RepositoryCommitModel) o;

        return new EqualsBuilder()
            .append(commitId, that.commitId)
            .append(version, that.version)
            .append(scmMetaDataModel, that.scmMetaDataModel)
            .append(nextCommit, that.nextCommit)
            .append(bugfixCommit, that.bugfixCommit)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(commitId)
            .append(version)
            .append(scmMetaDataModel)
            .append(nextCommit)
            .append(bugfixCommit)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("commitId", commitId)
            .append("version", version)
            .append("createdAt", createdAt)
            .append("scmMetaDataModel", scmMetaDataModel)
            .append("nextCommit", nextCommit)
            .append("bugfixCommit", bugfixCommit)
            .toString();
    }

    @Override
    public int compareTo(RepositoryCommit o) {
        return getVersion().compareTo(o.getVersion());
    }
}
