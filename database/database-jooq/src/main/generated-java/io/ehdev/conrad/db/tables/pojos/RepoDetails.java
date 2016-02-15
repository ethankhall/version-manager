/**
 * This class is generated by jOOQ
 */
package io.ehdev.conrad.db.tables.pojos;


import java.io.Serializable;
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
@Table(name = "repo_details", schema = "public", uniqueConstraints = {
	@UniqueConstraint(columnNames = {"project_name", "repo_name"})
})
public class RepoDetails implements Serializable {

	private static final long serialVersionUID = -2040602875;

	private UUID    uuid;
	private String  projectName;
	private String  repoName;
	private UUID    projectUuid;
	private UUID    versionBumperUuid;
	private String  url;
	private String  description;
	private Boolean public_;

	public RepoDetails() {}

	public RepoDetails(RepoDetails value) {
		this.uuid = value.uuid;
		this.projectName = value.projectName;
		this.repoName = value.repoName;
		this.projectUuid = value.projectUuid;
		this.versionBumperUuid = value.versionBumperUuid;
		this.url = value.url;
		this.description = value.description;
		this.public_ = value.public_;
	}

	public RepoDetails(
		UUID    uuid,
		String  projectName,
		String  repoName,
		UUID    projectUuid,
		UUID    versionBumperUuid,
		String  url,
		String  description,
		Boolean public_
	) {
		this.uuid = uuid;
		this.projectName = projectName;
		this.repoName = repoName;
		this.projectUuid = projectUuid;
		this.versionBumperUuid = versionBumperUuid;
		this.url = url;
		this.description = description;
		this.public_ = public_;
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

	@Column(name = "project_name", nullable = false, length = 255)
	@NotNull
	@Size(max = 255)
	public String getProjectName() {
		return this.projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@Column(name = "repo_name", nullable = false, length = 255)
	@NotNull
	@Size(max = 255)
	public String getRepoName() {
		return this.repoName;
	}

	public void setRepoName(String repoName) {
		this.repoName = repoName;
	}

	@Column(name = "project_uuid")
	public UUID getProjectUuid() {
		return this.projectUuid;
	}

	public void setProjectUuid(UUID projectUuid) {
		this.projectUuid = projectUuid;
	}

	@Column(name = "version_bumper_uuid")
	public UUID getVersionBumperUuid() {
		return this.versionBumperUuid;
	}

	public void setVersionBumperUuid(UUID versionBumperUuid) {
		this.versionBumperUuid = versionBumperUuid;
	}

	@Column(name = "url", nullable = false, length = 1024)
	@NotNull
	@Size(max = 1024)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "description", nullable = false)
	@NotNull
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "public")
	public Boolean getPublic() {
		return this.public_;
	}

	public void setPublic(Boolean public_) {
		this.public_ = public_;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("RepoDetails (");

		sb.append(uuid);
		sb.append(", ").append(projectName);
		sb.append(", ").append(repoName);
		sb.append(", ").append(projectUuid);
		sb.append(", ").append(versionBumperUuid);
		sb.append(", ").append(url);
		sb.append(", ").append(description);
		sb.append(", ").append(public_);

		sb.append(")");
		return sb.toString();
	}
}