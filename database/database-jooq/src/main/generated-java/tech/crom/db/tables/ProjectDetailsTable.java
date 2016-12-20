/**
 * This class is generated by jOOQ
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
import tech.crom.db.tables.records.ProjectDetailsRecord;


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
public class ProjectDetailsTable extends TableImpl<ProjectDetailsRecord> {

    private static final long serialVersionUID = -771046148;

    /**
     * The reference instance of <code>version_manager_test.project_details</code>
     */
    public static final ProjectDetailsTable PROJECT_DETAILS = new ProjectDetailsTable();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ProjectDetailsRecord> getRecordType() {
        return ProjectDetailsRecord.class;
    }

    /**
     * The column <code>version_manager_test.project_details.product_detail_id</code>.
     */
    public final TableField<ProjectDetailsRecord, Long> PRODUCT_DETAIL_ID = createField("product_detail_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>version_manager_test.project_details.project_name</code>.
     */
    public final TableField<ProjectDetailsRecord, String> PROJECT_NAME = createField("project_name", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false), this, "");

    /**
     * The column <code>version_manager_test.project_details.security_id</code>.
     */
    public final TableField<ProjectDetailsRecord, Long> SECURITY_ID = createField("security_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * Create a <code>version_manager_test.project_details</code> table reference
     */
    public ProjectDetailsTable() {
        this("project_details", null);
    }

    /**
     * Create an aliased <code>version_manager_test.project_details</code> table reference
     */
    public ProjectDetailsTable(String alias) {
        this(alias, PROJECT_DETAILS);
    }

    private ProjectDetailsTable(String alias, Table<ProjectDetailsRecord> aliased) {
        this(alias, aliased, null);
    }

    private ProjectDetailsTable(String alias, Table<ProjectDetailsRecord> aliased, Field<?>[] parameters) {
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
    public Identity<ProjectDetailsRecord, Long> getIdentity() {
        return Keys.IDENTITY_PROJECT_DETAILS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ProjectDetailsRecord> getPrimaryKey() {
        return Keys.KEY_PROJECT_DETAILS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ProjectDetailsRecord>> getKeys() {
        return Arrays.<UniqueKey<ProjectDetailsRecord>>asList(Keys.KEY_PROJECT_DETAILS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<ProjectDetailsRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<ProjectDetailsRecord, ?>>asList(Keys.PROJECT_DETAILS_IBFK_1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProjectDetailsTable as(String alias) {
        return new ProjectDetailsTable(alias, this);
    }

    /**
     * Rename this table
     */
    public ProjectDetailsTable rename(String name) {
        return new ProjectDetailsTable(name, null);
    }
}