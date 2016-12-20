/**
 * This class is generated by jOOQ
 */
package tech.crom.db.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;

import tech.crom.db.Keys;
import tech.crom.db.VersionManagerTest;
import tech.crom.db.tables.records.VersionBumpersRecord;


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
public class VersionBumpersTable extends TableImpl<VersionBumpersRecord> {

    private static final long serialVersionUID = -50839877;

    /**
     * The reference instance of <code>version_manager_test.version_bumpers</code>
     */
    public static final VersionBumpersTable VERSION_BUMPERS = new VersionBumpersTable();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<VersionBumpersRecord> getRecordType() {
        return VersionBumpersRecord.class;
    }

    /**
     * The column <code>version_manager_test.version_bumpers.version_bumper_id</code>.
     */
    public final TableField<VersionBumpersRecord, Long> VERSION_BUMPER_ID = createField("version_bumper_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>version_manager_test.version_bumpers.bumper_name</code>.
     */
    public final TableField<VersionBumpersRecord, String> BUMPER_NAME = createField("bumper_name", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>version_manager_test.version_bumpers.class_name</code>.
     */
    public final TableField<VersionBumpersRecord, String> CLASS_NAME = createField("class_name", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>version_manager_test.version_bumpers.description</code>.
     */
    public final TableField<VersionBumpersRecord, String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * Create a <code>version_manager_test.version_bumpers</code> table reference
     */
    public VersionBumpersTable() {
        this("version_bumpers", null);
    }

    /**
     * Create an aliased <code>version_manager_test.version_bumpers</code> table reference
     */
    public VersionBumpersTable(String alias) {
        this(alias, VERSION_BUMPERS);
    }

    private VersionBumpersTable(String alias, Table<VersionBumpersRecord> aliased) {
        this(alias, aliased, null);
    }

    private VersionBumpersTable(String alias, Table<VersionBumpersRecord> aliased, Field<?>[] parameters) {
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
    public Identity<VersionBumpersRecord, Long> getIdentity() {
        return Keys.IDENTITY_VERSION_BUMPERS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<VersionBumpersRecord> getPrimaryKey() {
        return Keys.KEY_VERSION_BUMPERS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<VersionBumpersRecord>> getKeys() {
        return Arrays.<UniqueKey<VersionBumpersRecord>>asList(Keys.KEY_VERSION_BUMPERS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VersionBumpersTable as(String alias) {
        return new VersionBumpersTable(alias, this);
    }

    /**
     * Rename this table
     */
    public VersionBumpersTable rename(String name) {
        return new VersionBumpersTable(name, null);
    }
}