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
import javax.validation.constraints.NotNull;


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
@Table(name = "version_bumper", schema = "public")
public class VersionBumper implements Serializable {

	private static final long serialVersionUID = -1081718122;

	private UUID   uuid;
	private String bumperName;
	private String className;
	private String description;

	public VersionBumper() {}

	public VersionBumper(VersionBumper value) {
		this.uuid = value.uuid;
		this.bumperName = value.bumperName;
		this.className = value.className;
		this.description = value.description;
	}

	public VersionBumper(
		UUID   uuid,
		String bumperName,
		String className,
		String description
	) {
		this.uuid = uuid;
		this.bumperName = bumperName;
		this.className = className;
		this.description = description;
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

	@Column(name = "bumper_name", unique = true, nullable = false)
	@NotNull
	public String getBumperName() {
		return this.bumperName;
	}

	public void setBumperName(String bumperName) {
		this.bumperName = bumperName;
	}

	@Column(name = "class_name", unique = true, nullable = false)
	@NotNull
	public String getClassName() {
		return this.className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	@Column(name = "description", nullable = false)
	@NotNull
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("VersionBumper (");

		sb.append(uuid);
		sb.append(", ").append(bumperName);
		sb.append(", ").append(className);
		sb.append(", ").append(description);

		sb.append(")");
		return sb.toString();
	}
}
