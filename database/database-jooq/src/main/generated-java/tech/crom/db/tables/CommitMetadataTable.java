/*
 * This file is generated by jOOQ.
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
import tech.crom.db.tables.records.CommitMetadataRecord;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class CommitMetadataTable extends TableImpl<CommitMetadataRecord> {

    private static final long serialVersionUID = -230117838;

    /**
     * The reference instance of <code>version_manager_test.commit_metadata</code>
     */
    public static final CommitMetadataTable COMMIT_METADATA = new CommitMetadataTable();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<CommitMetadataRecord> getRecordType() {
        return CommitMetadataRecord.class;
    }

    /**
     * The column <code>version_manager_test.commit_metadata.commit_metadata_id</code>.
     */
    public final TableField<CommitMetadataRecord, Long> COMMIT_METADATA_ID = createField("commit_metadata_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>version_manager_test.commit_metadata.commit_id</code>.
     */
    public final TableField<CommitMetadataRecord, Long> COMMIT_ID = createField("commit_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>version_manager_test.commit_metadata.project_id</code>.
     */
    public final TableField<CommitMetadataRecord, Long> PROJECT_ID = createField("project_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>version_manager_test.commit_metadata.repo_id</code>.
     */
    public final TableField<CommitMetadataRecord, Long> REPO_ID = createField("repo_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>version_manager_test.commit_metadata.name</code>.
     */
    public final TableField<CommitMetadataRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.length(128).nullable(false), this, "");

    /**
     * The column <code>version_manager_test.commit_metadata.uri</code>.
     */
    public final TableField<CommitMetadataRecord, String> URI = createField("uri", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>version_manager_test.commit_metadata.size</code>.
     */
    public final TableField<CommitMetadataRecord, Long> SIZE = createField("size", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>version_manager_test.commit_metadata.content_type</code>.
     */
    public final TableField<CommitMetadataRecord, String> CONTENT_TYPE = createField("content_type", org.jooq.impl.SQLDataType.VARCHAR.length(64).nullable(false), this, "");

    /**
     * The column <code>version_manager_test.commit_metadata.created_at</code>.
     */
    public final TableField<CommitMetadataRecord, Instant> CREATED_AT = createField("created_at", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP(6)", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "", new TimestampConverter());

    /**
     * The column <code>version_manager_test.commit_metadata.updated_at</code>.
     */
    public final TableField<CommitMetadataRecord, Instant> UPDATED_AT = createField("updated_at", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP(6)", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "", new TimestampConverter());

    /**
     * Create a <code>version_manager_test.commit_metadata</code> table reference
     */
    public CommitMetadataTable() {
        this("commit_metadata", null);
    }

    /**
     * Create an aliased <code>version_manager_test.commit_metadata</code> table reference
     */
    public CommitMetadataTable(String alias) {
        this(alias, COMMIT_METADATA);
    }

    private CommitMetadataTable(String alias, Table<CommitMetadataRecord> aliased) {
        this(alias, aliased, null);
    }

    private CommitMetadataTable(String alias, Table<CommitMetadataRecord> aliased, Field<?>[] parameters) {
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
    public Identity<CommitMetadataRecord, Long> getIdentity() {
        return Keys.IDENTITY_COMMIT_METADATA;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<CommitMetadataRecord> getPrimaryKey() {
        return Keys.KEY_COMMIT_METADATA_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<CommitMetadataRecord>> getKeys() {
        return Arrays.<UniqueKey<CommitMetadataRecord>>asList(Keys.KEY_COMMIT_METADATA_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<CommitMetadataRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<CommitMetadataRecord, ?>>asList(Keys.COMMIT_METADATA_IBFK_1, Keys.COMMIT_METADATA_IBFK_2, Keys.COMMIT_METADATA_IBFK_3);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommitMetadataTable as(String alias) {
        return new CommitMetadataTable(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public CommitMetadataTable rename(String name) {
        return new CommitMetadataTable(name, null);
    }
}
