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

import tech.crom.db.Keys;
import tech.crom.db.VersionManagerTest;
import tech.crom.db.tables.records.UserDetailsRecord;


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
public class UserDetailsTable extends TableImpl<UserDetailsRecord> {

    private static final long serialVersionUID = -1513474742;

    /**
     * The reference instance of <code>version_manager_test.user_details</code>
     */
    public static final UserDetailsTable USER_DETAILS = new UserDetailsTable();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UserDetailsRecord> getRecordType() {
        return UserDetailsRecord.class;
    }

    /**
     * The column <code>version_manager_test.user_details.user_id</code>.
     */
    public final TableField<UserDetailsRecord, Long> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>version_manager_test.user_details.user_name</code>.
     */
    public final TableField<UserDetailsRecord, String> USER_NAME = createField("user_name", org.jooq.impl.SQLDataType.VARCHAR.length(128).nullable(false), this, "");

    /**
     * The column <code>version_manager_test.user_details.name</code>.
     */
    public final TableField<UserDetailsRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false), this, "");

    /**
     * Create a <code>version_manager_test.user_details</code> table reference
     */
    public UserDetailsTable() {
        this("user_details", null);
    }

    /**
     * Create an aliased <code>version_manager_test.user_details</code> table reference
     */
    public UserDetailsTable(String alias) {
        this(alias, USER_DETAILS);
    }

    private UserDetailsTable(String alias, Table<UserDetailsRecord> aliased) {
        this(alias, aliased, null);
    }

    private UserDetailsTable(String alias, Table<UserDetailsRecord> aliased, Field<?>[] parameters) {
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
    public Identity<UserDetailsRecord, Long> getIdentity() {
        return Keys.IDENTITY_USER_DETAILS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<UserDetailsRecord> getPrimaryKey() {
        return Keys.KEY_USER_DETAILS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<UserDetailsRecord>> getKeys() {
        return Arrays.<UniqueKey<UserDetailsRecord>>asList(Keys.KEY_USER_DETAILS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDetailsTable as(String alias) {
        return new UserDetailsTable(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public UserDetailsTable rename(String name) {
        return new UserDetailsTable(name, null);
    }
}
