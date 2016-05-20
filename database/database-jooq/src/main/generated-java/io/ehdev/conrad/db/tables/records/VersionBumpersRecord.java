/**
 * This class is generated by jOOQ
 */
package io.ehdev.conrad.db.tables.records;


import io.ehdev.conrad.db.tables.VersionBumpersTable;

import java.util.UUID;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
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
@Table(name = "version_bumpers", schema = "public")
public class VersionBumpersRecord extends UpdatableRecordImpl<VersionBumpersRecord> implements Record4<UUID, String, String, String> {

    private static final long serialVersionUID = 437802900;

    /**
     * Setter for <code>public.version_bumpers.uuid</code>.
     */
    public void setUuid(UUID value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.version_bumpers.uuid</code>.
     */
    @Id
    @Column(name = "uuid", unique = true, nullable = false)
    public UUID getUuid() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>public.version_bumpers.bumper_name</code>.
     */
    public void setBumperName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.version_bumpers.bumper_name</code>.
     */
    @Column(name = "bumper_name", unique = true, nullable = false)
    @NotNull
    public String getBumperName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.version_bumpers.class_name</code>.
     */
    public void setClassName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.version_bumpers.class_name</code>.
     */
    @Column(name = "class_name", unique = true, nullable = false)
    @NotNull
    public String getClassName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.version_bumpers.description</code>.
     */
    public void setDescription(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.version_bumpers.description</code>.
     */
    @Column(name = "description", nullable = false)
    @NotNull
    public String getDescription() {
        return (String) get(3);
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
    // Record4 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<UUID, String, String, String> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<UUID, String, String, String> valuesRow() {
        return (Row4) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UUID> field1() {
        return VersionBumpersTable.VERSION_BUMPERS.UUID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return VersionBumpersTable.VERSION_BUMPERS.BUMPER_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return VersionBumpersTable.VERSION_BUMPERS.CLASS_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return VersionBumpersTable.VERSION_BUMPERS.DESCRIPTION;
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
        return getBumperName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getClassName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getDescription();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VersionBumpersRecord value1(UUID value) {
        setUuid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VersionBumpersRecord value2(String value) {
        setBumperName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VersionBumpersRecord value3(String value) {
        setClassName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VersionBumpersRecord value4(String value) {
        setDescription(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VersionBumpersRecord values(UUID value1, String value2, String value3, String value4) {
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
     * Create a detached VersionBumpersRecord
     */
    public VersionBumpersRecord() {
        super(VersionBumpersTable.VERSION_BUMPERS);
    }

    /**
     * Create a detached, initialised VersionBumpersRecord
     */
    public VersionBumpersRecord(UUID uuid, String bumperName, String className, String description) {
        super(VersionBumpersTable.VERSION_BUMPERS);

        set(0, uuid);
        set(1, bumperName);
        set(2, className);
        set(3, description);
    }
}
