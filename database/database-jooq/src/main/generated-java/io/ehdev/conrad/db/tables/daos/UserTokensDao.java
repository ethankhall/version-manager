/**
 * This class is generated by jOOQ
 */
package io.ehdev.conrad.db.tables.daos;


import io.ehdev.conrad.db.tables.UserTokensTable;
import io.ehdev.conrad.db.tables.pojos.UserTokens;
import io.ehdev.conrad.db.tables.records.UserTokensRecord;

import java.time.Instant;
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
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserTokensDao extends DAOImpl<UserTokensRecord, UserTokens, UUID> {

    /**
     * Create a new UserTokensDao without any configuration
     */
    public UserTokensDao() {
        super(UserTokensTable.USER_TOKENS, UserTokens.class);
    }

    /**
     * Create a new UserTokensDao with an attached configuration
     */
    public UserTokensDao(Configuration configuration) {
        super(UserTokensTable.USER_TOKENS, UserTokens.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected UUID getId(UserTokens object) {
        return object.getUuid();
    }

    /**
     * Fetch records that have <code>uuid IN (values)</code>
     */
    public List<UserTokens> fetchByUuid(UUID... values) {
        return fetch(UserTokensTable.USER_TOKENS.UUID, values);
    }

    /**
     * Fetch a unique record that has <code>uuid = value</code>
     */
    public UserTokens fetchOneByUuid(UUID value) {
        return fetchOne(UserTokensTable.USER_TOKENS.UUID, value);
    }

    /**
     * Fetch records that have <code>created_at IN (values)</code>
     */
    public List<UserTokens> fetchByCreatedAt(Instant... values) {
        return fetch(UserTokensTable.USER_TOKENS.CREATED_AT, values);
    }

    /**
     * Fetch records that have <code>expires_at IN (values)</code>
     */
    public List<UserTokens> fetchByExpiresAt(Instant... values) {
        return fetch(UserTokensTable.USER_TOKENS.EXPIRES_AT, values);
    }

    /**
     * Fetch records that have <code>valid IN (values)</code>
     */
    public List<UserTokens> fetchByValid(Boolean... values) {
        return fetch(UserTokensTable.USER_TOKENS.VALID, values);
    }

    /**
     * Fetch records that have <code>user_uuid IN (values)</code>
     */
    public List<UserTokens> fetchByUserUuid(UUID... values) {
        return fetch(UserTokensTable.USER_TOKENS.USER_UUID, values);
    }
}
