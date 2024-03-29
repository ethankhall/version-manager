/*
 * This file is generated by jOOQ.
*/
package tech.crom.db.tables;


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
import tech.crom.db.tables.records.RepoDetailsRecord;


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
public class RepoDetailsTable extends TableImpl<RepoDetailsRecord> {

    private static final long serialVersionUID = 1576420040;

    /**
     * The reference instance of <code>version_manager_test.repo_details</code>
     */
    public static final RepoDetailsTable REPO_DETAILS = new RepoDetailsTable();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<RepoDetailsRecord> getRecordType() {
        return RepoDetailsRecord.class;
    }

    /**
     * The column <code>version_manager_test.repo_details.repo_detail_id</code>.
     */
    public final TableField<RepoDetailsRecord, Long> REPO_DETAIL_ID = createField("repo_detail_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>version_manager_test.repo_details.repo_name</code>.
     */
    public final TableField<RepoDetailsRecord, String> REPO_NAME = createField("repo_name", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false), this, "");

    /**
     * The column <code>version_manager_test.repo_details.project_id</code>.
     */
    public final TableField<RepoDetailsRecord, Long> PROJECT_ID = createField("project_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>version_manager_test.repo_details.version_bumper_id</code>.
     */
    public final TableField<RepoDetailsRecord, Long> VERSION_BUMPER_ID = createField("version_bumper_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>version_manager_test.repo_details.url</code>.
     */
    public final TableField<RepoDetailsRecord, String> URL = createField("url", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>version_manager_test.repo_details.description</code>.
     */
    public final TableField<RepoDetailsRecord, String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>version_manager_test.repo_details.public</code>.
     */
    public final TableField<RepoDetailsRecord, Boolean> PUBLIC = createField("public", org.jooq.impl.SQLDataType.BOOLEAN.defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.BOOLEAN)), this, "");

    /**
     * The column <code>version_manager_test.repo_details.security_id</code>.
     */
    public final TableField<RepoDetailsRecord, Long> SECURITY_ID = createField("security_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * Create a <code>version_manager_test.repo_details</code> table reference
     */
    public RepoDetailsTable() {
        this("repo_details", null);
    }

    /**
     * Create an aliased <code>version_manager_test.repo_details</code> table reference
     */
    public RepoDetailsTable(String alias) {
        this(alias, REPO_DETAILS);
    }

    private RepoDetailsTable(String alias, Table<RepoDetailsRecord> aliased) {
        this(alias, aliased, null);
    }

    private RepoDetailsTable(String alias, Table<RepoDetailsRecord> aliased, Field<?>[] parameters) {
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
    public Identity<RepoDetailsRecord, Long> getIdentity() {
        return Keys.IDENTITY_REPO_DETAILS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<RepoDetailsRecord> getPrimaryKey() {
        return Keys.KEY_REPO_DETAILS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<RepoDetailsRecord>> getKeys() {
        return Arrays.<UniqueKey<RepoDetailsRecord>>asList(Keys.KEY_REPO_DETAILS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<RepoDetailsRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<RepoDetailsRecord, ?>>asList(Keys.REPO_DETAILS_IBFK_1, Keys.REPO_DETAILS_IBFK_2, Keys.REPO_DETAILS_IBFK_3);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RepoDetailsTable as(String alias) {
        return new RepoDetailsTable(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public RepoDetailsTable rename(String name) {
        return new RepoDetailsTable(name, null);
    }
}
