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
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;

import tech.crom.db.tables.VersionStateMachineStatesTable;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.3"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
@Entity
@Table(name = "version_state_machine_states", schema = "version_manager_test", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"version_state_machine_id", "state_name"})
}, indexes = {
    @Index(name = "PRIMARY", unique = true, columnList = "version_state_machine_state_id ASC"),
    @Index(name = "version_state_machine_id", unique = true, columnList = "version_state_machine_id ASC, state_name ASC")
})
public class VersionStateMachineStatesRecord extends UpdatableRecordImpl<VersionStateMachineStatesRecord> implements Record5<Long, Long, String, Boolean, String> {

    private static final long serialVersionUID = 1975671205;

    /**
     * Setter for <code>version_manager_test.version_state_machine_states.version_state_machine_state_id</code>.
     */
    public void setVersionStateMachineStateId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>version_manager_test.version_state_machine_states.version_state_machine_state_id</code>.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "version_state_machine_state_id", unique = true, nullable = false, precision = 19)
    public Long getVersionStateMachineStateId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>version_manager_test.version_state_machine_states.version_state_machine_id</code>.
     */
    public void setVersionStateMachineId(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>version_manager_test.version_state_machine_states.version_state_machine_id</code>.
     */
    @Column(name = "version_state_machine_id", nullable = false, precision = 19)
    @NotNull
    public Long getVersionStateMachineId() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>version_manager_test.version_state_machine_states.state_name</code>.
     */
    public void setStateName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>version_manager_test.version_state_machine_states.state_name</code>.
     */
    @Column(name = "state_name", nullable = false, length = 64)
    @NotNull
    @Size(max = 64)
    public String getStateName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>version_manager_test.version_state_machine_states.auto_transition</code>.
     */
    public void setAutoTransition(Boolean value) {
        set(3, value);
    }

    /**
     * Getter for <code>version_manager_test.version_state_machine_states.auto_transition</code>.
     */
    @Column(name = "auto_transition")
    public Boolean getAutoTransition() {
        return (Boolean) get(3);
    }

    /**
     * Setter for <code>version_manager_test.version_state_machine_states.next_state</code>.
     */
    public void setNextState(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>version_manager_test.version_state_machine_states.next_state</code>.
     */
    @Column(name = "next_state", length = 64)
    @Size(max = 64)
    public String getNextState() {
        return (String) get(4);
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
    // Record5 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<Long, Long, String, Boolean, String> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<Long, Long, String, Boolean, String> valuesRow() {
        return (Row5) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return VersionStateMachineStatesTable.VERSION_STATE_MACHINE_STATES.VERSION_STATE_MACHINE_STATE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field2() {
        return VersionStateMachineStatesTable.VERSION_STATE_MACHINE_STATES.VERSION_STATE_MACHINE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return VersionStateMachineStatesTable.VERSION_STATE_MACHINE_STATES.STATE_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Boolean> field4() {
        return VersionStateMachineStatesTable.VERSION_STATE_MACHINE_STATES.AUTO_TRANSITION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return VersionStateMachineStatesTable.VERSION_STATE_MACHINE_STATES.NEXT_STATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long component1() {
        return getVersionStateMachineStateId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long component2() {
        return getVersionStateMachineId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getStateName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean component4() {
        return getAutoTransition();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getNextState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value1() {
        return getVersionStateMachineStateId();
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
    public String value3() {
        return getStateName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean value4() {
        return getAutoTransition();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getNextState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VersionStateMachineStatesRecord value1(Long value) {
        setVersionStateMachineStateId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VersionStateMachineStatesRecord value2(Long value) {
        setVersionStateMachineId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VersionStateMachineStatesRecord value3(String value) {
        setStateName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VersionStateMachineStatesRecord value4(Boolean value) {
        setAutoTransition(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VersionStateMachineStatesRecord value5(String value) {
        setNextState(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VersionStateMachineStatesRecord values(Long value1, Long value2, String value3, Boolean value4, String value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached VersionStateMachineStatesRecord
     */
    public VersionStateMachineStatesRecord() {
        super(VersionStateMachineStatesTable.VERSION_STATE_MACHINE_STATES);
    }

    /**
     * Create a detached, initialised VersionStateMachineStatesRecord
     */
    public VersionStateMachineStatesRecord(Long versionStateMachineStateId, Long versionStateMachineId, String stateName, Boolean autoTransition, String nextState) {
        super(VersionStateMachineStatesTable.VERSION_STATE_MACHINE_STATES);

        set(0, versionStateMachineStateId);
        set(1, versionStateMachineId);
        set(2, stateName);
        set(3, autoTransition);
        set(4, nextState);
    }
}
