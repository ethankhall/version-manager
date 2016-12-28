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
import tech.crom.db.tables.records.ProjectDetailTrackerRecord;


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
public class ProjectDetailTrackerTable extends TableImpl<ProjectDetailTrackerRecord> {

    private static final long serialVersionUID = -302326454;

    /**
     * The reference instance of <code>version_manager_test.project_detail_tracker</code>
     */
    public static final ProjectDetailTrackerTable PROJECT_DETAIL_TRACKER = new ProjectDetailTrackerTable();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ProjectDetailTrackerRecord> getRecordType() {
        return ProjectDetailTrackerRecord.class;
    }

    /**
     * The column <code>version_manager_test.project_detail_tracker.project_detail_tracker_id</code>.
     */
    public final TableField<ProjectDetailTrackerRecord, Long> PROJECT_DETAIL_TRACKER_ID = createField("project_detail_tracker_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>version_manager_test.project_detail_tracker.product_detail_id</code>.
     */
    public final TableField<ProjectDetailTrackerRecord, Long> PRODUCT_DETAIL_ID = createField("product_detail_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>version_manager_test.project_detail_tracker.user_id</code>.
     */
    public final TableField<ProjectDetailTrackerRecord, Long> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * Create a <code>version_manager_test.project_detail_tracker</code> table reference
     */
    public ProjectDetailTrackerTable() {
        this("project_detail_tracker", null);
    }

    /**
     * Create an aliased <code>version_manager_test.project_detail_tracker</code> table reference
     */
    public ProjectDetailTrackerTable(String alias) {
        this(alias, PROJECT_DETAIL_TRACKER);
    }

    private ProjectDetailTrackerTable(String alias, Table<ProjectDetailTrackerRecord> aliased) {
        this(alias, aliased, null);
    }

    private ProjectDetailTrackerTable(String alias, Table<ProjectDetailTrackerRecord> aliased, Field<?>[] parameters) {
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
    public Identity<ProjectDetailTrackerRecord, Long> getIdentity() {
        return Keys.IDENTITY_PROJECT_DETAIL_TRACKER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ProjectDetailTrackerRecord> getPrimaryKey() {
        return Keys.KEY_PROJECT_DETAIL_TRACKER_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ProjectDetailTrackerRecord>> getKeys() {
        return Arrays.<UniqueKey<ProjectDetailTrackerRecord>>asList(Keys.KEY_PROJECT_DETAIL_TRACKER_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<ProjectDetailTrackerRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<ProjectDetailTrackerRecord, ?>>asList(Keys.PROJECT_DETAIL_TRACKER_IBFK_1, Keys.PROJECT_DETAIL_TRACKER_IBFK_2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProjectDetailTrackerTable as(String alias) {
        return new ProjectDetailTrackerTable(alias, this);
    }

    /**
     * Rename this table
     */
    public ProjectDetailTrackerTable rename(String name) {
        return new ProjectDetailTrackerTable(name, null);
    }
}
