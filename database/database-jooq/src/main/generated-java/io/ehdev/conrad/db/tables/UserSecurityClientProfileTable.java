/**
 * This class is generated by jOOQ
 */
package io.ehdev.conrad.db.tables;


import io.ehdev.conrad.db.Keys;
import io.ehdev.conrad.db.Public;
import io.ehdev.conrad.db.tables.records.UserSecurityClientProfileRecord;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


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
public class UserSecurityClientProfileTable extends TableImpl<UserSecurityClientProfileRecord> {

	private static final long serialVersionUID = 1996823183;

	/**
	 * The reference instance of <code>public.user_security_client_profile</code>
	 */
	public static final UserSecurityClientProfileTable USER_SECURITY_CLIENT_PROFILE = new UserSecurityClientProfileTable();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<UserSecurityClientProfileRecord> getRecordType() {
		return UserSecurityClientProfileRecord.class;
	}

	/**
	 * The column <code>public.user_security_client_profile.uuid</code>.
	 */
	public final TableField<UserSecurityClientProfileRecord, UUID> UUID = createField("uuid", org.jooq.impl.SQLDataType.UUID.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>public.user_security_client_profile.user_uuid</code>.
	 */
	public final TableField<UserSecurityClientProfileRecord, UUID> USER_UUID = createField("user_uuid", org.jooq.impl.SQLDataType.UUID.nullable(false), this, "");

	/**
	 * The column <code>public.user_security_client_profile.provider_type</code>.
	 */
	public final TableField<UserSecurityClientProfileRecord, String> PROVIDER_TYPE = createField("provider_type", org.jooq.impl.SQLDataType.VARCHAR.length(64).nullable(false), this, "");

	/**
	 * The column <code>public.user_security_client_profile.provider_user_id</code>.
	 */
	public final TableField<UserSecurityClientProfileRecord, String> PROVIDER_USER_ID = createField("provider_user_id", org.jooq.impl.SQLDataType.VARCHAR.length(128).nullable(false), this, "");

	/**
	 * Create a <code>public.user_security_client_profile</code> table reference
	 */
	public UserSecurityClientProfileTable() {
		this("user_security_client_profile", null);
	}

	/**
	 * Create an aliased <code>public.user_security_client_profile</code> table reference
	 */
	public UserSecurityClientProfileTable(String alias) {
		this(alias, USER_SECURITY_CLIENT_PROFILE);
	}

	private UserSecurityClientProfileTable(String alias, Table<UserSecurityClientProfileRecord> aliased) {
		this(alias, aliased, null);
	}

	private UserSecurityClientProfileTable(String alias, Table<UserSecurityClientProfileRecord> aliased, Field<?>[] parameters) {
		super(alias, Public.PUBLIC, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<UserSecurityClientProfileRecord> getPrimaryKey() {
		return Keys.USER_SECURITY_CLIENT_PROFILE_PKEY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<UserSecurityClientProfileRecord>> getKeys() {
		return Arrays.<UniqueKey<UserSecurityClientProfileRecord>>asList(Keys.USER_SECURITY_CLIENT_PROFILE_PKEY, Keys.USER_SECURITY_CLIENT_PROFILE_PROVIDER_TYPE_PROVIDER_USER_ID_KEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ForeignKey<UserSecurityClientProfileRecord, ?>> getReferences() {
		return Arrays.<ForeignKey<UserSecurityClientProfileRecord, ?>>asList(Keys.USER_SECURITY_CLIENT_PROFILE__USER_SECURITY_CLIENT_PROFILE_USER_UUID_FKEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserSecurityClientProfileTable as(String alias) {
		return new UserSecurityClientProfileTable(alias, this);
	}

	/**
	 * Rename this table
	 */
	public UserSecurityClientProfileTable rename(String name) {
		return new UserSecurityClientProfileTable(name, null);
	}
}
