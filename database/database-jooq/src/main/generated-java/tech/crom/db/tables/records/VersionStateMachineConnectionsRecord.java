/*
 * This file is generated by jOOQ.
*/
package tech.crom.db.tables.records;


import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;

import tech.crom.db.tables.VersionStateMachineConnectionsTable;


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
@Entity
@Table(name = "version_state_machine_connections", schema = "version_manager_test", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"source", "destination"})
})
public class VersionStateMachineConnectionsRecord extends UpdatableRecordImpl<VersionStateMachineConnectionsRecord> implements Record4<Long, Long, Long, Long> {

    private static final long serialVersionUID = -302115470;

    /**
     * Setter for <code>version_manager_test.version_state_machine_connections.state_machine_detail_id</code>.
     */
    public void setStateMachineDetailId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>version_manager_test.version_state_machine_connections.state_machine_detail_id</code>.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "state_machine_detail_id", unique = true, nullable = false, precision = 19)
    @NotNull
    public Long getStateMachineDetailId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>version_manager_test.version_state_machine_connections.version_state_machine_id</code>.
     */
    public void setVersionStateMachineId(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>version_manager_test.version_state_machine_connections.version_state_machine_id</code>.
     */
    @Column(name = "version_state_machine_id", nullable = false, precision = 19)
    @NotNull
    public Long getVersionStateMachineId() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>version_manager_test.version_state_machine_connections.source</code>.
     */
    public void setSource(Long value) {
        set(2, value);
    }

    /**
     * Getter for <code>version_manager_test.version_state_machine_connections.source</code>.
     */
    @Column(name = "source", nullable = false, precision = 19)
    @NotNull
    public Long getSource() {
        return (Long) get(2);
    }

    /**
     * Setter for <code>version_manager_test.version_state_machine_connections.destination</code>.
     */
    public void setDestination(Long value) {
        set(3, value);
    }

    /**
     * Getter for <code>version_manager_test.version_state_machine_connections.destination</code>.
     */
    @Column(name = "destination", nullable = false, precision = 19)
    @NotNull
    public Long getDestination() {
        return (Long) get(3);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<Long, Long, Long, Long> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<Long, Long, Long, Long> valuesRow() {
        return (Row4) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return VersionStateMachineConnectionsTable.VERSION_STATE_MACHINE_CONNECTIONS.STATE_MACHINE_DETAIL_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field2() {
        return VersionStateMachineConnectionsTable.VERSION_STATE_MACHINE_CONNECTIONS.VERSION_STATE_MACHINE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field3() {
        return VersionStateMachineConnectionsTable.VERSION_STATE_MACHINE_CONNECTIONS.SOURCE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field4() {
        return VersionStateMachineConnectionsTable.VERSION_STATE_MACHINE_CONNECTIONS.DESTINATION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value1() {
        return getStateMachineDetailId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value2() {
        return getVersionStateMachineId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value3() {
        return getSource();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value4() {
        return getDestination();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VersionStateMachineConnectionsRecord value1(Long value) {
        setStateMachineDetailId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VersionStateMachineConnectionsRecord value2(Long value) {
        setVersionStateMachineId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VersionStateMachineConnectionsRecord value3(Long value) {
        setSource(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VersionStateMachineConnectionsRecord value4(Long value) {
        setDestination(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VersionStateMachineConnectionsRecord values(Long value1, Long value2, Long value3, Long value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached VersionStateMachineConnectionsRecord
     */
    public VersionStateMachineConnectionsRecord() {
        super(VersionStateMachineConnectionsTable.VERSION_STATE_MACHINE_CONNECTIONS);
    }

    /**
     * Create a detached, initialised VersionStateMachineConnectionsRecord
     */
    public VersionStateMachineConnectionsRecord(Long stateMachineDetailId, Long versionStateMachineId, Long source, Long destination) {
        super(VersionStateMachineConnectionsTable.VERSION_STATE_MACHINE_CONNECTIONS);

        set(0, stateMachineDetailId);
        set(1, versionStateMachineId);
        set(2, source);
        set(3, destination);
    }
}
