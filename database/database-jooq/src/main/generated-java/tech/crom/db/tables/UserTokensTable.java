/**
 * This class is generated by jOOQ
 */
package tech.crom.db.tables;


import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;

import tech.crom.db.Keys;
import tech.crom.db.VersionManagerTest;
import tech.crom.db.converter.TimestampConverter;
import tech.crom.db.tables.records.UserTokensRecord;


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
public class UserTokensTable extends TableImpl<UserTokensRecord> {

    private static final long serialVersionUID = 2075728997;

    /**
     * The reference instance of <code>version_manager_test.user_tokens</code>
     */
    public static final UserTokensTable USER_TOKENS = new UserTokensTable();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UserTokensRecord> getRecordType() {
        return UserTokensRecord.class;
    }

    /**
     * The column <code>version_manager_test.user_tokens.user_token_id</code>.
     */
    public final TableField<UserTokensRecord, Long> USER_TOKEN_ID = createField("user_token_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>version_manager_test.user_tokens.public_user_token</code>.
     */
    public final TableField<UserTokensRecord, String> PUBLIC_USER_TOKEN = createField("public_user_token", org.jooq.impl.SQLDataType.VARCHAR.length(64).nullable(false), this, "");

    /**
     * The column <code>version_manager_test.user_tokens.created_at</code>.
     */
    public final TableField<UserTokensRecord, Instant> CREATED_AT = createField("created_at", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP(6)", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "", new TimestampConverter());

    /**
     * The column <code>version_manager_test.user_tokens.expires_at</code>.
     */
    public final TableField<UserTokensRecord, Instant> EXPIRES_AT = createField("expires_at", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP(6)", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "", new TimestampConverter());

    /**
     * The column <code>version_manager_test.user_tokens.valid</code>.
     */
    public final TableField<UserTokensRecord, Boolean> VALID = createField("valid", org.jooq.impl.SQLDataType.BOOLEAN.defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.BOOLEAN)), this, "");

    /**
     * The column <code>version_manager_test.user_tokens.user_id</code>.
     */
    public final TableField<UserTokensRecord, Long> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * Create a <code>version_manager_test.user_tokens</code> table reference
     */
    public UserTokensTable() {
        this("user_tokens", null);
    }

    /**
     * Create an aliased <code>version_manager_test.user_tokens</code> table reference
     */
    public UserTokensTable(String alias) {
        this(alias, USER_TOKENS);
    }

    private UserTokensTable(String alias, Table<UserTokensRecord> aliased) {
        this(alias, aliased, null);
    }

    private UserTokensTable(String alias, Table<UserTokensRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return VersionManagerTest.VERSION_MANAGER_TEST;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<UserTokensRecord, Long> getIdentity() {
        return Keys.IDENTITY_USER_TOKENS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<UserTokensRecord> getPrimaryKey() {
        return Keys.KEY_USER_TOKENS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<UserTokensRecord>> getKeys() {
        return Arrays.<UniqueKey<UserTokensRecord>>asList(Keys.KEY_USER_TOKENS_PRIMARY, Keys.KEY_USER_TOKENS_PUBLIC_USER_TOKEN);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<UserTokensRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<UserTokensRecord, ?>>asList(Keys.USER_TOKENS_IBFK_1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserTokensTable as(String alias) {
        return new UserTokensTable(alias, this);
    }

    /**
     * Rename this table
     */
    public UserTokensTable rename(String name) {
        return new UserTokensTable(name, null);
    }
}
