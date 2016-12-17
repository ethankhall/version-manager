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
import org.jooq.types.UInteger;
import org.jooq.types.ULong;

import tech.crom.db.Keys;
import tech.crom.db.VersionManagerTest;
import tech.crom.db.tables.records.AclEntryRecord;


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
public class AclEntryTable extends TableImpl<AclEntryRecord> {

    private static final long serialVersionUID = 1472825342;

    /**
     * The reference instance of <code>version_manager_test.acl_entry</code>
     */
    public static final AclEntryTable ACL_ENTRY = new AclEntryTable();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<AclEntryRecord> getRecordType() {
        return AclEntryRecord.class;
    }

    /**
     * The column <code>version_manager_test.acl_entry.id</code>.
     */
    public final TableField<AclEntryRecord, ULong> ID = createField("id", org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false), this, "");

    /**
     * The column <code>version_manager_test.acl_entry.acl_object_identity</code>.
     */
    public final TableField<AclEntryRecord, ULong> ACL_OBJECT_IDENTITY = createField("acl_object_identity", org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false), this, "");

    /**
     * The column <code>version_manager_test.acl_entry.ace_order</code>.
     */
    public final TableField<AclEntryRecord, Integer> ACE_ORDER = createField("ace_order", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>version_manager_test.acl_entry.sid</code>.
     */
    public final TableField<AclEntryRecord, ULong> SID = createField("sid", org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false), this, "");

    /**
     * The column <code>version_manager_test.acl_entry.mask</code>.
     */
    public final TableField<AclEntryRecord, UInteger> MASK = createField("mask", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false), this, "");

    /**
     * The column <code>version_manager_test.acl_entry.granting</code>.
     */
    public final TableField<AclEntryRecord, Boolean> GRANTING = createField("granting", org.jooq.impl.SQLDataType.BOOLEAN.nullable(false), this, "");

    /**
     * The column <code>version_manager_test.acl_entry.audit_success</code>.
     */
    public final TableField<AclEntryRecord, Boolean> AUDIT_SUCCESS = createField("audit_success", org.jooq.impl.SQLDataType.BOOLEAN.nullable(false), this, "");

    /**
     * The column <code>version_manager_test.acl_entry.audit_failure</code>.
     */
    public final TableField<AclEntryRecord, Boolean> AUDIT_FAILURE = createField("audit_failure", org.jooq.impl.SQLDataType.BOOLEAN.nullable(false), this, "");

    /**
     * Create a <code>version_manager_test.acl_entry</code> table reference
     */
    public AclEntryTable() {
        this("acl_entry", null);
    }

    /**
     * Create an aliased <code>version_manager_test.acl_entry</code> table reference
     */
    public AclEntryTable(String alias) {
        this(alias, ACL_ENTRY);
    }

    private AclEntryTable(String alias, Table<AclEntryRecord> aliased) {
        this(alias, aliased, null);
    }

    private AclEntryTable(String alias, Table<AclEntryRecord> aliased, Field<?>[] parameters) {
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
    public Identity<AclEntryRecord, ULong> getIdentity() {
        return Keys.IDENTITY_ACL_ENTRY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<AclEntryRecord> getPrimaryKey() {
        return Keys.KEY_ACL_ENTRY_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<AclEntryRecord>> getKeys() {
        return Arrays.<UniqueKey<AclEntryRecord>>asList(Keys.KEY_ACL_ENTRY_PRIMARY, Keys.KEY_ACL_ENTRY_UNIQUE_ACL_ENTRY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<AclEntryRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<AclEntryRecord, ?>>asList(Keys.FK_ACL_ENTRY_OBJECT, Keys.FK_ACL_ENTRY_ACL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AclEntryTable as(String alias) {
        return new AclEntryTable(alias, this);
    }

    /**
     * Rename this table
     */
    public AclEntryTable rename(String name) {
        return new AclEntryTable(name, null);
    }
}
