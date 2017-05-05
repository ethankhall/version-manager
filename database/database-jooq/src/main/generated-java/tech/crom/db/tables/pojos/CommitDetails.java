/*
 * This file is generated by jOOQ.
*/
package tech.crom.db.tables.pojos;


import java.io.Serializable;
import java.time.Instant;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
@Entity
@Table(name = "commit_details", schema = "version_manager_test")
public class CommitDetails implements Serializable {

    private static final long serialVersionUID = -326606137;

    private Long    commitDetailId;
    private Long    repoDetailId;
    private Long    parentCommitId;
    private String  commitId;
    private String  version;
    private Instant createdAt;

    public CommitDetails() {}

    public CommitDetails(CommitDetails value) {
        this.commitDetailId = value.commitDetailId;
        this.repoDetailId = value.repoDetailId;
        this.parentCommitId = value.parentCommitId;
        this.commitId = value.commitId;
        this.version = value.version;
        this.createdAt = value.createdAt;
    }

    public CommitDetails(
        Long    commitDetailId,
        Long    repoDetailId,
        Long    parentCommitId,
        String  commitId,
        String  version,
        Instant createdAt
    ) {
        this.commitDetailId = commitDetailId;
        this.repoDetailId = repoDetailId;
        this.parentCommitId = parentCommitId;
        this.commitId = commitId;
        this.version = version;
        this.createdAt = createdAt;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commit_detail_id", unique = true, nullable = false, precision = 19)
    @NotNull
    public Long getCommitDetailId() {
        return this.commitDetailId;
    }

    public void setCommitDetailId(Long commitDetailId) {
        this.commitDetailId = commitDetailId;
    }

    @Column(name = "repo_detail_id", nullable = false, precision = 19)
    @NotNull
    public Long getRepoDetailId() {
        return this.repoDetailId;
    }

    public void setRepoDetailId(Long repoDetailId) {
        this.repoDetailId = repoDetailId;
    }

    @Column(name = "parent_commit_id", precision = 19)
    public Long getParentCommitId() {
        return this.parentCommitId;
    }

    public void setParentCommitId(Long parentCommitId) {
        this.parentCommitId = parentCommitId;
    }

    @Column(name = "commit_id", nullable = false, length = 40)
    @NotNull
    @Size(max = 40)
    public String getCommitId() {
        return this.commitId;
    }

    public void setCommitId(String commitId) {
        this.commitId = commitId;
    }

    @Column(name = "version", nullable = false, length = 120)
    @NotNull
    @Size(max = 120)
    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Column(name = "created_at", nullable = false)
    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("CommitDetails (");

        sb.append(commitDetailId);
        sb.append(", ").append(repoDetailId);
        sb.append(", ").append(parentCommitId);
        sb.append(", ").append(commitId);
        sb.append(", ").append(version);
        sb.append(", ").append(createdAt);

        sb.append(")");
        return sb.toString();
    }
}
