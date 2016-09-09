/**
 * This class is generated by jOOQ
 */
package io.ehdev.conrad.db.tables;


import io.ehdev.conrad.db.Keys;
import io.ehdev.conrad.db.Public;
import io.ehdev.conrad.db.converter.TimestampConverter;
import io.ehdev.conrad.db.tables.records.CommitDetailsRecord;

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
        "jOOQ version:3.8.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class CommitDetailsTable extends TableImpl<CommitDetailsRecord> {

    private static final long serialVersionUID = -224382307;

    /**
     * The reference instance of <code>public.commit_details</code>
     */
    public static final CommitDetailsTable COMMIT_DETAILS = new CommitDetailsTable();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<CommitDetailsRecord> getRecordType() {
        return CommitDetailsRecord.class;
    }

    /**
     * The column <code>public.commit_details.uuid</code>.
     */
    public final TableField<CommitDetailsRecord, UUID> UUID = createField("uuid", org.jooq.impl.SQLDataType.UUID.nullable(false).defaultValue(org.jooq.impl.DSL.field("uuid_generate_v4()", org.jooq.impl.SQLDataType.UUID)), this, "");

    /**
     * The column <code>public.commit_details.repo_details_uuid</code>.
     */
    public final TableField<CommitDetailsRecord, UUID> REPO_DETAILS_UUID = createField("repo_details_uuid", org.jooq.impl.SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.commit_details.parent_commit_uuid</code>.
     */
    public final TableField<CommitDetailsRecord, UUID> PARENT_COMMIT_UUID = createField("parent_commit_uuid", org.jooq.impl.SQLDataType.UUID, this, "");

    /**
     * The column <code>public.commit_details.commit_id</code>.
     */
    public final TableField<CommitDetailsRecord, String> COMMIT_ID = createField("commit_id", org.jooq.impl.SQLDataType.VARCHAR.length(40).nullable(false), this, "");

    /**
     * The column <code>public.commit_details.version</code>.
     */
    public final TableField<CommitDetailsRecord, String> VERSION = createField("version", org.jooq.impl.SQLDataType.VARCHAR.length(120).nullable(false), this, "");

    /**
     * The column <code>public.commit_details.created_at</code>.
     */
    public final TableField<CommitDetailsRecord, Instant> CREATED_AT = createField("created_at", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "", new TimestampConverter());

    /**
     * Create a <code>public.commit_details</code> table reference
     */
    public CommitDetailsTable() {
        this("commit_details", null);
    }

    /**
     * Create an aliased <code>public.commit_details</code> table reference
     */
    public CommitDetailsTable(String alias) {
        this(alias, COMMIT_DETAILS);
    }

    private CommitDetailsTable(String alias, Table<CommitDetailsRecord> aliased) {
        this(alias, aliased, null);
    }

    private CommitDetailsTable(String alias, Table<CommitDetailsRecord> aliased, Field<?>[] parameters) {
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
    public UniqueKey<CommitDetailsRecord> getPrimaryKey() {
        return Keys.COMMIT_DETAILS_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<CommitDetailsRecord>> getKeys() {
        return Arrays.<UniqueKey<CommitDetailsRecord>>asList(Keys.COMMIT_DETAILS_PKEY, Keys.COMMIT_DETAILS_COMMIT_ID_REPO_DETAILS_UUID_KEY, Keys.COMMIT_DETAILS_VERSION_REPO_DETAILS_UUID_KEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<CommitDetailsRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<CommitDetailsRecord, ?>>asList(Keys.COMMIT_DETAILS__COMMIT_DETAILS_REPO_DETAILS_UUID_FKEY, Keys.COMMIT_DETAILS__COMMIT_DETAILS_PARENT_COMMIT_UUID_FKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommitDetailsTable as(String alias) {
        return new CommitDetailsTable(alias, this);
    }

    /**
     * Rename this table
     */
    public CommitDetailsTable rename(String name) {
        return new CommitDetailsTable(name, null);
    }
}
