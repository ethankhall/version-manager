/**
 * This class is generated by jOOQ
 */
package io.ehdev.conrad.db.tables.records;


import io.ehdev.conrad.db.tables.TokenJoinTable;

import java.util.UUID;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
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
@Table(name = "token_join", schema = "public")
public class TokenJoinRecord extends UpdatableRecordImpl<TokenJoinRecord> implements Record5<UUID, UUID, UUID, UUID, UUID> {

	private static final long serialVersionUID = -1854159010;

	/**
	 * Setter for <code>public.token_join.uuid</code>.
	 */
	public void setUuid(UUID value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>public.token_join.uuid</code>.
	 */
	@Id
	@Column(name = "uuid", unique = true, nullable = false)
	@NotNull
	public UUID getUuid() {
		return (UUID) getValue(0);
	}

	/**
	 * Setter for <code>public.token_join.token</code>.
	 */
	public void setToken(UUID value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>public.token_join.token</code>.
	 */
	@Column(name = "token", unique = true, nullable = false)
	@NotNull
	public UUID getToken() {
		return (UUID) getValue(1);
	}

	/**
	 * Setter for <code>public.token_join.project_uuid</code>.
	 */
	public void setProjectUuid(UUID value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>public.token_join.project_uuid</code>.
	 */
	@Column(name = "project_uuid")
	public UUID getProjectUuid() {
		return (UUID) getValue(2);
	}

	/**
	 * Setter for <code>public.token_join.repo_uuid</code>.
	 */
	public void setRepoUuid(UUID value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>public.token_join.repo_uuid</code>.
	 */
	@Column(name = "repo_uuid")
	public UUID getRepoUuid() {
		return (UUID) getValue(3);
	}

	/**
	 * Setter for <code>public.token_join.user_uuid</code>.
	 */
	public void setUserUuid(UUID value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>public.token_join.user_uuid</code>.
	 */
	@Column(name = "user_uuid")
	public UUID getUserUuid() {
		return (UUID) getValue(4);
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
	// Record5 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row5<UUID, UUID, UUID, UUID, UUID> fieldsRow() {
		return (Row5) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row5<UUID, UUID, UUID, UUID, UUID> valuesRow() {
		return (Row5) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<UUID> field1() {
		return TokenJoinTable.TOKEN_JOIN.UUID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<UUID> field2() {
		return TokenJoinTable.TOKEN_JOIN.TOKEN;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<UUID> field3() {
		return TokenJoinTable.TOKEN_JOIN.PROJECT_UUID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<UUID> field4() {
		return TokenJoinTable.TOKEN_JOIN.REPO_UUID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<UUID> field5() {
		return TokenJoinTable.TOKEN_JOIN.USER_UUID;
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
	public UUID value2() {
		return getToken();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UUID value3() {
		return getProjectUuid();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UUID value4() {
		return getRepoUuid();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UUID value5() {
		return getUserUuid();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TokenJoinRecord value1(UUID value) {
		setUuid(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TokenJoinRecord value2(UUID value) {
		setToken(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TokenJoinRecord value3(UUID value) {
		setProjectUuid(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TokenJoinRecord value4(UUID value) {
		setRepoUuid(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TokenJoinRecord value5(UUID value) {
		setUserUuid(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TokenJoinRecord values(UUID value1, UUID value2, UUID value3, UUID value4, UUID value5) {
		value1(value1);
		value2(value2);
		value3(value3);
		value4(value4);
		value5(value5);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached TokenJoinRecord
	 */
	public TokenJoinRecord() {
		super(TokenJoinTable.TOKEN_JOIN);
	}

	/**
	 * Create a detached, initialised TokenJoinRecord
	 */
	public TokenJoinRecord(UUID uuid, UUID token, UUID projectUuid, UUID repoUuid, UUID userUuid) {
		super(TokenJoinTable.TOKEN_JOIN);

		setValue(0, uuid);
		setValue(1, token);
		setValue(2, projectUuid);
		setValue(3, repoUuid);
		setValue(4, userUuid);
	}
}
