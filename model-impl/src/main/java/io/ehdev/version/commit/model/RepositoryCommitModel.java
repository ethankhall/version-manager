package io.ehdev.version.commit.model;

import io.ehdev.version.commit.CommitVersion;
import io.ehdev.version.commit.RepositoryCommit;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

@Table(
    uniqueConstraints = @UniqueConstraint(columnNames = {"commitId", "scmMetaData"})
)
@Entity
public class RepositoryCommitModel implements RepositoryCommit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "commitId", length = 40)
    String commitId;

    @Convert(converter = CommitVersionConverter.class)
    @Column(name = "version", length = 128)
    CommitVersion version;

    @JoinColumn(name = "scmMetaData")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    ScmMetaDataModel scmMetaDataModel;

    @OneToOne(fetch = FetchType.LAZY)
    RepositoryCommitModel nextCommit;

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
}
