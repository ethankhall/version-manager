/**
 * This class is generated by jOOQ
 */
package io.ehdev.conrad.db.tables.records;


import io.ehdev.conrad.db.tables.ProjectDetailsTable;

import java.util.UUID;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;


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
@Entity
@Table(name = "project_details", schema = "public")
public class ProjectDetailsRecord extends UpdatableRecordImpl<ProjectDetailsRecord> implements Record3<UUID, String, Long> {

    private static final long serialVersionUID = 285616101;

    /**
     * Setter for <code>public.project_details.uuid</code>.
     */
    public void setUuid(UUID value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.project_details.uuid</code>.
     */
    @Id
    @Column(name = "uuid", unique = true, nullable = false)
    public UUID getUuid() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>public.project_details.project_name</code>.
     */
    public void setProjectName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.project_details.project_name</code>.
     */
    @Column(name = "project_name", unique = true, nullable = false, length = 255)
    @NotNull
    @Size(max = 255)
    public String getProjectName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.project_details.security_id</code>.
     */
    public void setSecurityId(Long value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.project_details.security_id</code>.
     */
    @Column(name = "security_id", nullable = false, precision = 64)
    public Long getSecurityId() {
        return (Long) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<UUID> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<UUID, String, Long> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<UUID, String, Long> valuesRow() {
        return (Row3) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UUID> field1() {
        return ProjectDetailsTable.PROJECT_DETAILS.UUID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return ProjectDetailsTable.PROJECT_DETAILS.PROJECT_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field3() {
        return ProjectDetailsTable.PROJECT_DETAILS.SECURITY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UUID value1() {
        return getUuid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getProjectName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value3() {
        return getSecurityId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProjectDetailsRecord value1(UUID value) {
        setUuid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProjectDetailsRecord value2(String value) {
        setProjectName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProjectDetailsRecord value3(Long value) {
        setSecurityId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProjectDetailsRecord values(UUID value1, String value2, Long value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ProjectDetailsRecord
     */
    public ProjectDetailsRecord() {
        super(ProjectDetailsTable.PROJECT_DETAILS);
    }

    /**
     * Create a detached, initialised ProjectDetailsRecord
     */
    public ProjectDetailsRecord(UUID uuid, String projectName, Long securityId) {
        super(ProjectDetailsTable.PROJECT_DETAILS);

        set(0, uuid);
        set(1, projectName);
        set(2, securityId);
    }
}
