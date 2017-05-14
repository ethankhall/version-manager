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
import tech.crom.db.tables.records.VersionStateMachineConnectionsRecord;


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
public class VersionStateMachineConnectionsTable extends TableImpl<VersionStateMachineConnectionsRecord> {

    private static final long serialVersionUID = 45331571;

    /**
     * The reference instance of <code>version_manager_test.version_state_machine_connections</code>
     */
    public static final VersionStateMachineConnectionsTable VERSION_STATE_MACHINE_CONNECTIONS = new VersionStateMachineConnectionsTable();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<VersionStateMachineConnectionsRecord> getRecordType() {
        return VersionStateMachineConnectionsRecord.class;
    }

    /**
     * The column <code>version_manager_test.version_state_machine_connections.state_machine_detail_id</code>.
     */
    public final TableField<VersionStateMachineConnectionsRecord, Long> STATE_MACHINE_DETAIL_ID = createField("state_machine_detail_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>version_manager_test.version_state_machine_connections.version_state_machine_id</code>.
     */
    public final TableField<VersionStateMachineConnectionsRecord, Long> VERSION_STATE_MACHINE_ID = createField("version_state_machine_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>version_manager_test.version_state_machine_connections.source</code>.
     */
    public final TableField<VersionStateMachineConnectionsRecord, Long> SOURCE = createField("source", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>version_manager_test.version_state_machine_connections.destination</code>.
     */
    public final TableField<VersionStateMachineConnectionsRecord, Long> DESTINATION = createField("destination", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * Create a <code>version_manager_test.version_state_machine_connections</code> table reference
     */
    public VersionStateMachineConnectionsTable() {
        this("version_state_machine_connections", null);
    }

    /**
     * Create an aliased <code>version_manager_test.version_state_machine_connections</code> table reference
     */
    public VersionStateMachineConnectionsTable(String alias) {
        this(alias, VERSION_STATE_MACHINE_CONNECTIONS);
    }

    private VersionStateMachineConnectionsTable(String alias, Table<VersionStateMachineConnectionsRecord> aliased) {
        this(alias, aliased, null);
    }

    private VersionStateMachineConnectionsTable(String alias, Table<VersionStateMachineConnectionsRecord> aliased, Field<?>[] parameters) {
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
    public Identity<VersionStateMachineConnectionsRecord, Long> getIdentity() {
        return Keys.IDENTITY_VERSION_STATE_MACHINE_CONNECTIONS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<VersionStateMachineConnectionsRecord> getPrimaryKey() {
        return Keys.KEY_VERSION_STATE_MACHINE_CONNECTIONS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<VersionStateMachineConnectionsRecord>> getKeys() {
        return Arrays.<UniqueKey<VersionStateMachineConnectionsRecord>>asList(Keys.KEY_VERSION_STATE_MACHINE_CONNECTIONS_PRIMARY, Keys.KEY_VERSION_STATE_MACHINE_CONNECTIONS_SOURCE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<VersionStateMachineConnectionsRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<VersionStateMachineConnectionsRecord, ?>>asList(Keys.VERSION_STATE_MACHINE_CONNECTIONS_IBFK_1, Keys.VERSION_STATE_MACHINE_CONNECTIONS_IBFK_2, Keys.VERSION_STATE_MACHINE_CONNECTIONS_IBFK_3);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VersionStateMachineConnectionsTable as(String alias) {
        return new VersionStateMachineConnectionsTable(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public VersionStateMachineConnectionsTable rename(String name) {
        return new VersionStateMachineConnectionsTable(name, null);
    }
}