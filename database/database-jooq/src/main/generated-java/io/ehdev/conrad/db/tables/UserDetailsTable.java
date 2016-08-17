/**
 * This class is generated by jOOQ
 */
package io.ehdev.conrad.db.tables;


import io.ehdev.conrad.db.Keys;
import io.ehdev.conrad.db.Public;
import io.ehdev.conrad.db.tables.records.UserDetailsRecord;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.annotation.Generated;

import org.jooq.Field;
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
public class UserDetailsTable extends TableImpl<UserDetailsRecord> {

    private static final long serialVersionUID = -696233275;

    /**
     * The reference instance of <code>public.user_details</code>
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
     * The column <code>public.user_details.uuid</code>.
     */
    public final TableField<UserDetailsRecord, UUID> UUID = createField("uuid", org.jooq.impl.SQLDataType.UUID.nullable(false).defaultValue(org.jooq.impl.DSL.field("uuid_generate_v4()", org.jooq.impl.SQLDataType.UUID)), this, "");

    /**
     * The column <code>public.user_details.user_name</code>.
     */
    public final TableField<UserDetailsRecord, String> USER_NAME = createField("user_name", org.jooq.impl.SQLDataType.VARCHAR.length(128).nullable(false), this, "");

    /**
     * The column <code>public.user_details.name</code>.
     */
    public final TableField<UserDetailsRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false), this, "");

    /**
     * Create a <code>public.user_details</code> table reference
     */
    public UserDetailsTable() {
        this("user_details", null);
    }

    /**
     * Create an aliased <code>public.user_details</code> table reference
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
        return Public.PUBLIC;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<UserDetailsRecord> getPrimaryKey() {
        return Keys.USER_DETAILS_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<UserDetailsRecord>> getKeys() {
        return Arrays.<UniqueKey<UserDetailsRecord>>asList(Keys.USER_DETAILS_PKEY, Keys.USER_DETAILS_USER_NAME_KEY);
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
    public UserDetailsTable rename(String name) {
        return new UserDetailsTable(name, null);
    }
}
