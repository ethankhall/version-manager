/**
 * This class is generated by jOOQ
 */
package io.ehdev.conrad.db.tables.records;


import io.ehdev.conrad.db.tables.RepoDetailsTable;

import java.util.UUID;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record8;
import org.jooq.Row8;
import org.jooq.impl.UpdatableRecordImpl;


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
public class RepoDetailsRecord extends UpdatableRecordImpl<RepoDetailsRecord> implements Record8<UUID, String, String, UUID, UUID, String, String, Boolean> {

	private static final long serialVersionUID = -57351302;

	/**
	 * Setter for <code>public.repo_details.uuid</code>.
	 */
	public void setUuid(UUID value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>public.repo_details.uuid</code>.
	 */
	@Id
	@Column(name = "uuid", unique = true, nullable = false)
	@NotNull
	public UUID getUuid() {
		return (UUID) getValue(0);
	}

	/**
	 * Setter for <code>public.repo_details.project_name</code>.
	 */
	public void setProjectName(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>public.repo_details.project_name</code>.
	 */
	@Column(name = "project_name", nullable = false, length = 255)
	@NotNull
	@Size(max = 255)
	public String getProjectName() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>public.repo_details.repo_name</code>.
	 */
	public void setRepoName(String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>public.repo_details.repo_name</code>.
	 */
	@Column(name = "repo_name", nullable = false, length = 255)
	@NotNull
	@Size(max = 255)
	public String getRepoName() {
		return (String) getValue(2);
	}

	/**
	 * Setter for <code>public.repo_details.project_uuid</code>.
	 */
	public void setProjectUuid(UUID value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>public.repo_details.project_uuid</code>.
	 */
	@Column(name = "project_uuid")
	public UUID getProjectUuid() {
		return (UUID) getValue(3);
	}

	/**
	 * Setter for <code>public.repo_details.version_bumper_uuid</code>.
	 */
	public void setVersionBumperUuid(UUID value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>public.repo_details.version_bumper_uuid</code>.
	 */
	@Column(name = "version_bumper_uuid")
	public UUID getVersionBumperUuid() {
		return (UUID) getValue(4);
	}

	/**
	 * Setter for <code>public.repo_details.url</code>.
	 */
	public void setUrl(String value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>public.repo_details.url</code>.
	 */
	@Column(name = "url", length = 1024)
	@Size(max = 1024)
	public String getUrl() {
		return (String) getValue(5);
	}

	/**
	 * Setter for <code>public.repo_details.description</code>.
	 */
	public void setDescription(String value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>public.repo_details.description</code>.
	 */
	@Column(name = "description", nullable = false)
	@NotNull
	public String getDescription() {
		return (String) getValue(6);
	}

	/**
	 * Setter for <code>public.repo_details.public</code>.
	 */
	public void setPublic(Boolean value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>public.repo_details.public</code>.
	 */
	@Column(name = "public")
	public Boolean getPublic() {
		return (Boolean) getValue(7);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Record1<UUID> key() {
		return (Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record8 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row8<UUID, String, String, UUID, UUID, String, String, Boolean> fieldsRow() {
		return (Row8) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row8<UUID, String, String, UUID, UUID, String, String, Boolean> valuesRow() {
		return (Row8) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<UUID> field1() {
		return RepoDetailsTable.REPO_DETAILS.UUID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return RepoDetailsTable.REPO_DETAILS.PROJECT_NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field3() {
		return RepoDetailsTable.REPO_DETAILS.REPO_NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<UUID> field4() {
		return RepoDetailsTable.REPO_DETAILS.PROJECT_UUID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<UUID> field5() {
		return RepoDetailsTable.REPO_DETAILS.VERSION_BUMPER_UUID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field6() {
		return RepoDetailsTable.REPO_DETAILS.URL;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field7() {
		return RepoDetailsTable.REPO_DETAILS.DESCRIPTION;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Boolean> field8() {
		return RepoDetailsTable.REPO_DETAILS.PUBLIC;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UUID value1() {
		return getUuid();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value2() {
		return getProjectName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value3() {
		return getRepoName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UUID value4() {
		return getProjectUuid();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UUID value5() {
		return getVersionBumperUuid();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value6() {
		return getUrl();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value7() {
		return getDescription();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean value8() {
		return getPublic();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RepoDetailsRecord value1(UUID value) {
		setUuid(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RepoDetailsRecord value2(String value) {
		setProjectName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RepoDetailsRecord value3(String value) {
		setRepoName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RepoDetailsRecord value4(UUID value) {
		setProjectUuid(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RepoDetailsRecord value5(UUID value) {
		setVersionBumperUuid(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RepoDetailsRecord value6(String value) {
		setUrl(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RepoDetailsRecord value7(String value) {
		setDescription(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RepoDetailsRecord value8(Boolean value) {
		setPublic(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RepoDetailsRecord values(UUID value1, String value2, String value3, UUID value4, UUID value5, String value6, String value7, Boolean value8) {
		value1(value1);
		value2(value2);
		value3(value3);
		value4(value4);
		value5(value5);
		value6(value6);
		value7(value7);
		value8(value8);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached RepoDetailsRecord
	 */
	public RepoDetailsRecord() {
		super(RepoDetailsTable.REPO_DETAILS);
	}

	/**
	 * Create a detached, initialised RepoDetailsRecord
	 */
	public RepoDetailsRecord(UUID uuid, String projectName, String repoName, UUID projectUuid, UUID versionBumperUuid, String url, String description, Boolean public_) {
		super(RepoDetailsTable.REPO_DETAILS);

		setValue(0, uuid);
		setValue(1, projectName);
		setValue(2, repoName);
		setValue(3, projectUuid);
		setValue(4, versionBumperUuid);
		setValue(5, url);
		setValue(6, description);
		setValue(7, public_);
	}
}
