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
		"jOOQ version:3.7.2"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
@Entity
@Table(name = "commit_metadata", schema = "public", uniqueConstraints = {
	@UniqueConstraint(columnNames = {"name", "commit_uuid"})
})
public class CommitMetadata implements Serializable {

	private static final long serialVersionUID = -1567135573;

	private UUID    uuid;
	private UUID    commitUuid;
	private UUID    repoDetailsUuid;
	private String  name;
	private String  text;
	private Instant createdAt;
	private Instant updatedAt;

	public CommitMetadata() {}

	public CommitMetadata(CommitMetadata value) {
		this.uuid = value.uuid;
		this.commitUuid = value.commitUuid;
		this.repoDetailsUuid = value.repoDetailsUuid;
		this.name = value.name;
		this.text = value.text;
		this.createdAt = value.createdAt;
		this.updatedAt = value.updatedAt;
	}

	public CommitMetadata(
		UUID    uuid,
		UUID    commitUuid,
		UUID    repoDetailsUuid,
		String  name,
		String  text,
		Instant createdAt,
		Instant updatedAt
	) {
		this.uuid = uuid;
		this.commitUuid = commitUuid;
		this.repoDetailsUuid = repoDetailsUuid;
		this.name = name;
		this.text = text;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	@Id
	@Column(name = "uuid", unique = true, nullable = false)
	@NotNull
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

	@Column(name = "text", nullable = false, length = 1048576)
	@NotNull
	@Size(max = 1048576)
	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("CommitMetadata (");

		sb.append(uuid);
		sb.append(", ").append(commitUuid);
		sb.append(", ").append(repoDetailsUuid);
		sb.append(", ").append(name);
		sb.append(", ").append(text);
		sb.append(", ").append(createdAt);
		sb.append(", ").append(updatedAt);

		sb.append(")");
		return sb.toString();
	}
}