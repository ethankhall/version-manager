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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


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
@Table(name = "commit_metadata", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"name", "commit_uuid"})
})
public class CommitMetadata implements Serializable {

    private static final long serialVersionUID = 1843720263;

    private UUID    uuid;
    private UUID    commitUuid;
    private UUID    repoDetailsUuid;
    private String  name;
    private String  uri;
    private Instant createdAt;
    private Instant updatedAt;
    private Long    size;

    public CommitMetadata() {}

    public CommitMetadata(CommitMetadata value) {
        this.uuid = value.uuid;
        this.commitUuid = value.commitUuid;
        this.repoDetailsUuid = value.repoDetailsUuid;
        this.name = value.name;
        this.uri = value.uri;
        this.createdAt = value.createdAt;
        this.updatedAt = value.updatedAt;
        this.size = value.size;
    }

    public CommitMetadata(
        UUID    uuid,
        UUID    commitUuid,
        UUID    repoDetailsUuid,
        String  name,
        String  uri,
        Instant createdAt,
        Instant updatedAt,
        Long    size
    ) {
        this.uuid = uuid;
        this.commitUuid = commitUuid;
        this.repoDetailsUuid = repoDetailsUuid;
        this.name = name;
        this.uri = uri;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.size = size;
    }

    @Id
    @Column(name = "uuid", unique = true, nullable = false)
    public UUID getUuid() {
        return this.uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Column(name = "commit_uuid")
    public UUID getCommitUuid() {
        return this.commitUuid;
    }

    public void setCommitUuid(UUID commitUuid) {
        this.commitUuid = commitUuid;
    }

    @Column(name = "repo_details_uuid")
    public UUID getRepoDetailsUuid() {
        return this.repoDetailsUuid;
    }

    public void setRepoDetailsUuid(UUID repoDetailsUuid) {
        this.repoDetailsUuid = repoDetailsUuid;
    }

    @Column(name = "name", nullable = false, length = 255)
    @NotNull
    @Size(max = 255)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "uri", nullable = false, length = 512)
    @NotNull
    @Size(max = 512)
    public String getUri() {
        return this.uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Column(name = "created_at", nullable = false)
    @NotNull
    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @Column(name = "updated_at", nullable = false)
    @NotNull
    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Column(name = "size", nullable = false, precision = 64)
    public Long getSize() {
        return this.size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("CommitMetadata (");

        sb.append(uuid);
        sb.append(", ").append(commitUuid);
        sb.append(", ").append(repoDetailsUuid);
        sb.append(", ").append(name);
        sb.append(", ").append(uri);
        sb.append(", ").append(createdAt);
        sb.append(", ").append(updatedAt);
        sb.append(", ").append(size);

        sb.append(")");
        return sb.toString();
    }
}
