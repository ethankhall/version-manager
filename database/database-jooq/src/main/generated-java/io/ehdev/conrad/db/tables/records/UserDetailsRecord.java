/**
 * This class is generated by jOOQ
 */
package io.ehdev.conrad.db.tables.records;


import io.ehdev.conrad.db.tables.UserDetailsTable;

import java.util.UUID;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
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
@Table(name = "user_details", schema = "public")
public class UserDetailsRecord extends UpdatableRecordImpl<UserDetailsRecord> implements Record4<UUID, String, String, String> {

	private static final long serialVersionUID = -563966535;

	/**
	 * Setter for <code>public.user_details.uuid</code>.
	 */
	public void setUuid(UUID value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>public.user_details.uuid</code>.
	 */
	@Id
	@Column(name = "uuid", unique = true, nullable = false)
	@NotNull
	public UUID getUuid() {
		return (UUID) getValue(0);
	}

	/**
	 * Setter for <code>public.user_details.user_name</code>.
	 */
	public void setUserName(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>public.user_details.user_name</code>.
	 */
	@Column(name = "user_name", unique = true, nullable = false, length = 128)
	@NotNull
	@Size(max = 128)
	public String getUserName() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>public.user_details.name</code>.
	 */
	public void setName(String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>public.user_details.name</code>.
	 */
	@Column(name = "name", nullable = false, length = 255)
	@NotNull
	@Size(max = 255)
	public String getName() {
		return (String) getValue(2);
	}

	/**
	 * Setter for <code>public.user_details.email_address</code>.
	 */
	public void setEmailAddress(String value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>public.user_details.email_address</code>.
	 */
	@Column(name = "email_address", nullable = false, length = 256)
	@NotNull
	@Size(max = 256)
	public String getEmailAddress() {
		return (String) getValue(3);
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
	// Record4 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row4<UUID, String, String, String> fieldsRow() {
		return (Row4) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row4<UUID, String, String, String> valuesRow() {
		return (Row4) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<UUID> field1() {
		return UserDetailsTable.USER_DETAILS.UUID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return UserDetailsTable.USER_DETAILS.USER_NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field3() {
		return UserDetailsTable.USER_DETAILS.NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field4() {
		return UserDetailsTable.USER_DETAILS.EMAIL_ADDRESS;
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
		return getUserName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value3() {
		return getName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value4() {
		return getEmailAddress();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserDetailsRecord value1(UUID value) {
		setUuid(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserDetailsRecord value2(String value) {
		setUserName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserDetailsRecord value3(String value) {
		setName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserDetailsRecord value4(String value) {
		setEmailAddress(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserDetailsRecord values(UUID value1, String value2, String value3, String value4) {
		value1(value1);
		value2(value2);
		value3(value3);
		value4(value4);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached UserDetailsRecord
	 */
	public UserDetailsRecord() {
		super(UserDetailsTable.USER_DETAILS);
	}

	/**
	 * Create a detached, initialised UserDetailsRecord
	 */
	public UserDetailsRecord(UUID uuid, String userName, String name, String emailAddress) {
		super(UserDetailsTable.USER_DETAILS);

		setValue(0, uuid);
		setValue(1, userName);
		setValue(2, name);
		setValue(3, emailAddress);
	}
}
