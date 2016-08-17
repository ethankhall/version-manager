/**
 * This class is generated by jOOQ
 */
package io.ehdev.conrad.db.tables.pojos;


import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
@Entity
@Table(name = "repository_tokens", schema = "public")
public class RepositoryTokens implements Serializable {

    private static final long serialVersionUID = -128451835;

    private UUID    uuid;
    private Instant createdAt;
    private Instant expiresAt;
    private Boolean valid;
    private UUID    repoUuid;

    public RepositoryTokens() {}

    public RepositoryTokens(RepositoryTokens value) {
        this.uuid = value.uuid;
        this.createdAt = value.createdAt;
        this.expiresAt = value.expiresAt;
        this.valid = value.valid;
        this.repoUuid = value.repoUuid;
    }

    public RepositoryTokens(
        UUID    uuid,
        Instant createdAt,
        Instant expiresAt,
        Boolean valid,
        UUID    repoUuid
    ) {
        this.uuid = uuid;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.valid = valid;
        this.repoUuid = repoUuid;
    }

    @Id
    @Column(name = "uuid", unique = true, nullable = false)
    public UUID getUuid() {
        return this.uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Column(name = "created_at", nullable = false)
    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @Column(name = "expires_at")
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

    @Column(name = "repo_uuid", nullable = false)
    @NotNull
    public UUID getRepoUuid() {
        return this.repoUuid;
    }

    public void setRepoUuid(UUID repoUuid) {
        this.repoUuid = repoUuid;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("RepositoryTokens (");

        sb.append(uuid);
        sb.append(", ").append(createdAt);
        sb.append(", ").append(expiresAt);
        sb.append(", ").append(valid);
        sb.append(", ").append(repoUuid);

        sb.append(")");
        return sb.toString();
    }
}
