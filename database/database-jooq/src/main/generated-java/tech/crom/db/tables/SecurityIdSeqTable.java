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
import tech.crom.db.tables.records.SecurityIdSeqRecord;


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
public class SecurityIdSeqTable extends TableImpl<SecurityIdSeqRecord> {

    private static final long serialVersionUID = 1317723293;

    /**
     * The reference instance of <code>version_manager_test.security_id_seq</code>
     */
    public static final SecurityIdSeqTable SECURITY_ID_SEQ = new SecurityIdSeqTable();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<SecurityIdSeqRecord> getRecordType() {
        return SecurityIdSeqRecord.class;
    }

    /**
     * The column <code>version_manager_test.security_id_seq.security_id</code>.
     */
    public final TableField<SecurityIdSeqRecord, Long> SECURITY_ID = createField("security_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>version_manager_test.security_id_seq.type</code>.
     */
    public final TableField<SecurityIdSeqRecord, String> TYPE = createField("type", org.jooq.impl.SQLDataType.VARCHAR.length(10), this, "");

    /**
     * Create a <code>version_manager_test.security_id_seq</code> table reference
     */
    public SecurityIdSeqTable() {
        this("security_id_seq", null);
    }

    /**
     * Create an aliased <code>version_manager_test.security_id_seq</code> table reference
     */
    public SecurityIdSeqTable(String alias) {
        this(alias, SECURITY_ID_SEQ);
    }

    private SecurityIdSeqTable(String alias, Table<SecurityIdSeqRecord> aliased) {
        this(alias, aliased, null);
    }

    private SecurityIdSeqTable(String alias, Table<SecurityIdSeqRecord> aliased, Field<?>[] parameters) {
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
    public Identity<SecurityIdSeqRecord, Long> getIdentity() {
        return Keys.IDENTITY_SECURITY_ID_SEQ;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<SecurityIdSeqRecord> getPrimaryKey() {
        return Keys.KEY_SECURITY_ID_SEQ_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<SecurityIdSeqRecord>> getKeys() {
        return Arrays.<UniqueKey<SecurityIdSeqRecord>>asList(Keys.KEY_SECURITY_ID_SEQ_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SecurityIdSeqTable as(String alias) {
        return new SecurityIdSeqTable(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public SecurityIdSeqTable rename(String name) {
        return new SecurityIdSeqTable(name, null);
    }
}
