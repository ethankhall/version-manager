/*
 * This file is generated by jOOQ.
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
import org.jooq.types.ULong;

import tech.crom.db.Keys;
import tech.crom.db.VersionManagerTest;
import tech.crom.db.tables.records.AclSidRecord;


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
public class AclSidTable extends TableImpl<AclSidRecord> {

    private static final long serialVersionUID = -911440224;

    /**
     * The reference instance of <code>version_manager_test.acl_sid</code>
     */
    public static final AclSidTable ACL_SID = new AclSidTable();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<AclSidRecord> getRecordType() {
        return AclSidRecord.class;
    }

    /**
     * The column <code>version_manager_test.acl_sid.id</code>.
     */
    public final TableField<AclSidRecord, ULong> ID = createField("id", org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false), this, "");

    /**
     * The column <code>version_manager_test.acl_sid.principal</code>.
     */
    public final TableField<AclSidRecord, Boolean> PRINCIPAL = createField("principal", org.jooq.impl.SQLDataType.BOOLEAN.nullable(false), this, "");

    /**
     * The column <code>version_manager_test.acl_sid.sid</code>.
     */
    public final TableField<AclSidRecord, String> SID = createField("sid", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "");

    /**
     * Create a <code>version_manager_test.acl_sid</code> table reference
     */
    public AclSidTable() {
        this("acl_sid", null);
    }

    /**
     * Create an aliased <code>version_manager_test.acl_sid</code> table reference
     */
    public AclSidTable(String alias) {
        this(alias, ACL_SID);
    }

    private AclSidTable(String alias, Table<AclSidRecord> aliased) {
        this(alias, aliased, null);
    }

    private AclSidTable(String alias, Table<AclSidRecord> aliased, Field<?>[] parameters) {
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
    public Identity<AclSidRecord, ULong> getIdentity() {
        return Keys.IDENTITY_ACL_SID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<AclSidRecord> getPrimaryKey() {
        return Keys.KEY_ACL_SID_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<AclSidRecord>> getKeys() {
        return Arrays.<UniqueKey<AclSidRecord>>asList(Keys.KEY_ACL_SID_PRIMARY, Keys.KEY_ACL_SID_UNIQUE_ACL_SID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AclSidTable as(String alias) {
        return new AclSidTable(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public AclSidTable rename(String name) {
        return new AclSidTable(name, null);
    }
}
