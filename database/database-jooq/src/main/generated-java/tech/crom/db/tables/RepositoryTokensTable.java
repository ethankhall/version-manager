/**
 * This class is generated by jOOQ
 */
package tech.crom.db.tables;


import java.sql.Timestamp;
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
import tech.crom.db.tables.records.RepositoryTokensRecord;


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
public class RepositoryTokensTable extends TableImpl<RepositoryTokensRecord> {

    private static final long serialVersionUID = -584880607;

    /**
     * The reference instance of <code>version_manager_test.repository_tokens</code>
     */
    public static final RepositoryTokensTable REPOSITORY_TOKENS = new RepositoryTokensTable();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<RepositoryTokensRecord> getRecordType() {
        return RepositoryTokensRecord.class;
    }

    /**
     * The column <code>version_manager_test.repository_tokens.id</code>.
     */
    public final TableField<RepositoryTokensRecord, Long> ID = createField("id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>version_manager_test.repository_tokens.created_at</code>.
     */
    public final TableField<RepositoryTokensRecord, Timestamp> CREATED_AT = createField("created_at", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>version_manager_test.repository_tokens.expires_at</code>.
     */
    public final TableField<RepositoryTokensRecord, Timestamp> EXPIRES_AT = createField("expires_at", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>version_manager_test.repository_tokens.valid</code>.
     */
    public final TableField<RepositoryTokensRecord, Byte> VALID = createField("valid", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.TINYINT)), this, "");

    /**
     * The column <code>version_manager_test.repository_tokens.repo_id</code>.
     */
    public final TableField<RepositoryTokensRecord, Long> REPO_ID = createField("repo_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * Create a <code>version_manager_test.repository_tokens</code> table reference
     */
    public RepositoryTokensTable() {
        this("repository_tokens", null);
    }

    /**
     * Create an aliased <code>version_manager_test.repository_tokens</code> table reference
     */
    public RepositoryTokensTable(String alias) {
        this(alias, REPOSITORY_TOKENS);
    }

    private RepositoryTokensTable(String alias, Table<RepositoryTokensRecord> aliased) {
        this(alias, aliased, null);
    }

    private RepositoryTokensTable(String alias, Table<RepositoryTokensRecord> aliased, Field<?>[] parameters) {
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
    public Identity<RepositoryTokensRecord, Long> getIdentity() {
        return Keys.IDENTITY_REPOSITORY_TOKENS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<RepositoryTokensRecord> getPrimaryKey() {
        return Keys.KEY_REPOSITORY_TOKENS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<RepositoryTokensRecord>> getKeys() {
        return Arrays.<UniqueKey<RepositoryTokensRecord>>asList(Keys.KEY_REPOSITORY_TOKENS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<RepositoryTokensRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<RepositoryTokensRecord, ?>>asList(Keys.REPOSITORY_TOKENS_IBFK_1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RepositoryTokensTable as(String alias) {
        return new RepositoryTokensTable(alias, this);
    }

    /**
     * Rename this table
     */
    public RepositoryTokensTable rename(String name) {
        return new RepositoryTokensTable(name, null);
    }
}
