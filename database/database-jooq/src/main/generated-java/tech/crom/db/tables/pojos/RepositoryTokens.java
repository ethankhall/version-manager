/**
 * This class is generated by jOOQ
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


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
@Entity
@Table(name = "repository_tokens", schema = "version_manager_test")
public class RepositoryTokens implements Serializable {

    private static final long serialVersionUID = -247904100;

    private Long    repositoryTokensId;
    private Instant createdAt;
    private Instant expiresAt;
    private Boolean valid;
    private Long    repoId;

    public RepositoryTokens() {}

    public RepositoryTokens(RepositoryTokens value) {
        this.repositoryTokensId = value.repositoryTokensId;
        this.createdAt = value.createdAt;
        this.expiresAt = value.expiresAt;
        this.valid = value.valid;
        this.repoId = value.repoId;
    }

    public RepositoryTokens(
        Long    repositoryTokensId,
        Instant createdAt,
        Instant expiresAt,
        Boolean valid,
        Long    repoId
    ) {
        this.repositoryTokensId = repositoryTokensId;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.valid = valid;
        this.repoId = repoId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "repository_tokens_id", unique = true, nullable = false, precision = 19)
    @NotNull
    public Long getRepositoryTokensId() {
        return this.repositoryTokensId;
    }

    public void setRepositoryTokensId(Long repositoryTokensId) {
        this.repositoryTokensId = repositoryTokensId;
    }

    @Column(name = "created_at", nullable = false)
    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @Column(name = "expires_at", nullable = false)
    public Instant getExpiresAt() {
        return this.expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    @Column(name = "valid")
    public Boolean getValid() {
        return this.valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    @Column(name = "repo_id", nullable = false, precision = 19)
    @NotNull
    public Long getRepoId() {
        return this.repoId;
    }

    public void setRepoId(Long repoId) {
        this.repoId = repoId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("RepositoryTokens (");

        sb.append(repositoryTokensId);
        sb.append(", ").append(createdAt);
        sb.append(", ").append(expiresAt);
        sb.append(", ").append(valid);
        sb.append(", ").append(repoId);

        sb.append(")");
        return sb.toString();
    }
}
