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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;

import tech.crom.db.tables.VersionStateMachineDefinitionsTable;


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
@Table(name = "version_state_machine_definitions", schema = "version_manager_test")
public class VersionStateMachineDefinitionsRecord extends UpdatableRecordImpl<VersionStateMachineDefinitionsRecord> implements Record3<Long, String, Long> {

    private static final long serialVersionUID = 1755060488;

    /**
     * Setter for <code>version_manager_test.version_state_machine_definitions.version_state_machine_id</code>.
     */
    public void setVersionStateMachineId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>version_manager_test.version_state_machine_definitions.version_state_machine_id</code>.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "version_state_machine_id", unique = true, nullable = false, precision = 19)
    @NotNull
    public Long getVersionStateMachineId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>version_manager_test.version_state_machine_definitions.initial_state</code>.
     */
    public void setInitialState(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>version_manager_test.version_state_machine_definitions.initial_state</code>.
     */
    @Column(name = "initial_state", nullable = false, length = 64)
    @NotNull
    @Size(max = 64)
    public String getInitialState() {
        return (String) get(1);
    }

    /**
     * Setter for <code>version_manager_test.version_state_machine_definitions.repo_detail_id</code>.
     */
    public void setRepoDetailId(Long value) {
        set(2, value);
    }

    /**
     * Getter for <code>version_manager_test.version_state_machine_definitions.repo_detail_id</code>.
     */
    @Column(name = "repo_detail_id", unique = true, nullable = false, precision = 19)
    @NotNull
    public Long getRepoDetailId() {
        return (Long) get(2);
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
    // Record3 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<Long, String, Long> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<Long, String, Long> valuesRow() {
        return (Row3) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return VersionStateMachineDefinitionsTable.VERSION_STATE_MACHINE_DEFINITIONS.VERSION_STATE_MACHINE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return VersionStateMachineDefinitionsTable.VERSION_STATE_MACHINE_DEFINITIONS.INITIAL_STATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field3() {
        return VersionStateMachineDefinitionsTable.VERSION_STATE_MACHINE_DEFINITIONS.REPO_DETAIL_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value1() {
        return getVersionStateMachineId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getInitialState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value3() {
        return getRepoDetailId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VersionStateMachineDefinitionsRecord value1(Long value) {
        setVersionStateMachineId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VersionStateMachineDefinitionsRecord value2(String value) {
        setInitialState(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VersionStateMachineDefinitionsRecord value3(Long value) {
        setRepoDetailId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VersionStateMachineDefinitionsRecord values(Long value1, String value2, Long value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached VersionStateMachineDefinitionsRecord
     */
    public VersionStateMachineDefinitionsRecord() {
        super(VersionStateMachineDefinitionsTable.VERSION_STATE_MACHINE_DEFINITIONS);
    }

    /**
     * Create a detached, initialised VersionStateMachineDefinitionsRecord
     */
    public VersionStateMachineDefinitionsRecord(Long versionStateMachineId, String initialState, Long repoDetailId) {
        super(VersionStateMachineDefinitionsTable.VERSION_STATE_MACHINE_DEFINITIONS);

        set(0, versionStateMachineId);
        set(1, initialState);
        set(2, repoDetailId);
    }
}
