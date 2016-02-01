/**
 * This class is generated by jOOQ
 */
package io.ehdev.conrad.db.tables.daos;


import io.ehdev.conrad.db.tables.UserSecurityClientProfileTable;
import io.ehdev.conrad.db.tables.pojos.UserSecurityClientProfile;
import io.ehdev.conrad.db.tables.records.UserSecurityClientProfileRecord;

import java.util.List;
import java.util.UUID;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


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
public class UserSecurityClientProfileDao extends DAOImpl<UserSecurityClientProfileRecord, UserSecurityClientProfile, UUID> {

	/**
	 * Create a new UserSecurityClientProfileDao without any configuration
	 */
	public UserSecurityClientProfileDao() {
		super(UserSecurityClientProfileTable.USER_SECURITY_CLIENT_PROFILE, UserSecurityClientProfile.class);
	}

	/**
	 * Create a new UserSecurityClientProfileDao with an attached configuration
	 */
	public UserSecurityClientProfileDao(Configuration configuration) {
		super(UserSecurityClientProfileTable.USER_SECURITY_CLIENT_PROFILE, UserSecurityClientProfile.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected UUID getId(UserSecurityClientProfile object) {
		return object.getUuid();
	}

	/**
	 * Fetch records that have <code>uuid IN (values)</code>
	 */
	public List<UserSecurityClientProfile> fetchByUuid(UUID... values) {
		return fetch(UserSecurityClientProfileTable.USER_SECURITY_CLIENT_PROFILE.UUID, values);
	}

	/**
	 * Fetch a unique record that has <code>uuid = value</code>
	 */
	public UserSecurityClientProfile fetchOneByUuid(UUID value) {
		return fetchOne(UserSecurityClientProfileTable.USER_SECURITY_CLIENT_PROFILE.UUID, value);
	}

	/**
	 * Fetch records that have <code>user_uuid IN (values)</code>
	 */
	public List<UserSecurityClientProfile> fetchByUserUuid(UUID... values) {
		return fetch(UserSecurityClientProfileTable.USER_SECURITY_CLIENT_PROFILE.USER_UUID, values);
	}

	/**
	 * Fetch records that have <code>provider_type IN (values)</code>
	 */
	public List<UserSecurityClientProfile> fetchByProviderType(String... values) {
		return fetch(UserSecurityClientProfileTable.USER_SECURITY_CLIENT_PROFILE.PROVIDER_TYPE, values);
	}

	/**
	 * Fetch records that have <code>provider_user_id IN (values)</code>
	 */
	public List<UserSecurityClientProfile> fetchByProviderUserId(String... values) {
		return fetch(UserSecurityClientProfileTable.USER_SECURITY_CLIENT_PROFILE.PROVIDER_USER_ID, values);
	}
}
