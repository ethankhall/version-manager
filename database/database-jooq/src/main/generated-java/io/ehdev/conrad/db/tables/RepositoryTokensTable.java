/**
 * This class is generated by jOOQ
 */
package io.ehdev.conrad.db.tables;


import io.ehdev.conrad.db.Keys;
import io.ehdev.conrad.db.Public;
import io.ehdev.conrad.db.converter.TimestampConverter;
import io.ehdev.conrad.db.tables.records.RepositoryTokensRecord;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Schema;
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
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class RepositoryTokensTable extends TableImpl<RepositoryTokensRecord> {

    private static final long serialVersionUID = 1538509394;

    /**
     * The reference instance of <code>public.repository_tokens</code>
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
     * The column <code>public.repository_tokens.uuid</code>.
     */
    public final TableField<RepositoryTokensRecord, UUID> UUID = createField("uuid", org.jooq.impl.SQLDataType.UUID.nullable(false).defaultValue(org.jooq.impl.DSL.field("uuid_generate_v4()", org.jooq.impl.SQLDataType.UUID)), this, "");

    /**
     * The column <code>public.repository_tokens.created_at</code>.
     */
    public final TableField<RepositoryTokensRecord, Instant> CREATED_AT = createField("created_at", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("now()", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "", new TimestampConverter());

    /**
     * The column <code>public.repository_tokens.expires_at</code>.
     */
    public final TableField<RepositoryTokensRecord, Instant> EXPIRES_AT = createField("expires_at", org.jooq.impl.SQLDataType.TIMESTAMP, this, "", new TimestampConverter());

    /**
     * The column <code>public.repository_tokens.valid</code>.
     */
    public final TableField<RepositoryTokensRecord, Boolean> VALID = createField("valid", org.jooq.impl.SQLDataType.BOOLEAN.defaultValue(org.jooq.impl.DSL.field("true", org.jooq.impl.SQLDataType.BOOLEAN)), this, "");

    /**
     * The column <code>public.repository_tokens.repo_uuid</code>.
     */
    public final TableField<RepositoryTokensRecord, UUID> REPO_UUID = createField("repo_uuid", org.jooq.impl.SQLDataType.UUID.nullable(false), this, "");

    /**
     * Create a <code>public.repository_tokens</code> table reference
     */
    public RepositoryTokensTable() {
        this("repository_tokens", null);
    }

    /**
     * Create an aliased <code>public.repository_tokens</code> table reference
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
        return Public.PUBLIC;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<RepositoryTokensRecord> getPrimaryKey() {
        return Keys.REPOSITORY_TOKENS_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<RepositoryTokensRecord>> getKeys() {
        return Arrays.<UniqueKey<RepositoryTokensRecord>>asList(Keys.REPOSITORY_TOKENS_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<RepositoryTokensRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<RepositoryTokensRecord, ?>>asList(Keys.REPOSITORY_TOKENS__REPOSITORY_TOKENS_REPO_UUID_FKEY);
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