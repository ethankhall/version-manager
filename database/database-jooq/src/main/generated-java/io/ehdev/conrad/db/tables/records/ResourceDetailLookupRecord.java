/**
 * This class is generated by jOOQ
 */
package io.ehdev.conrad.db.tables.records;


import io.ehdev.conrad.db.tables.ResourceDetailLookupTable;

import java.util.UUID;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
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
@Table(name = "resource_detail_lookup", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"project_name", "repo_name"}),
    @UniqueConstraint(columnNames = {"project_uuid", "repo_uuid"})
})
public class ResourceDetailLookupRecord extends UpdatableRecordImpl<ResourceDetailLookupRecord> implements Record5<UUID, String, String, UUID, UUID> {

    private static final long serialVersionUID = -823028579;

    /**
     * Setter for <code>public.resource_detail_lookup.uuid</code>.
     */
    public void setUuid(UUID value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.resource_detail_lookup.uuid</code>.
     */
    @Id
    @Column(name = "uuid", unique = true, nullable = false)
    public UUID getUuid() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>public.resource_detail_lookup.project_name</code>.
     */
    public void setProjectName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.resource_detail_lookup.project_name</code>.
     */
    @Column(name = "project_name", nullable = false, length = 255)
    @NotNull
    @Size(max = 255)
    public String getProjectName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.resource_detail_lookup.repo_name</code>.
     */
    public void setRepoName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.resource_detail_lookup.repo_name</code>.
     */
    @Column(name = "repo_name", length = 255)
    @Size(max = 255)
    public String getRepoName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.resource_detail_lookup.project_uuid</code>.
     */
    public void setProjectUuid(UUID value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.resource_detail_lookup.project_uuid</code>.
     */
    @Column(name = "project_uuid", nullable = false)
    @NotNull
    public UUID getProjectUuid() {
        return (UUID) get(3);
    }

    /**
     * Setter for <code>public.resource_detail_lookup.repo_uuid</code>.
     */
    public void setRepoUuid(UUID value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.resource_detail_lookup.repo_uuid</code>.
     */
    @Column(name = "repo_uuid", unique = true)
    public UUID getRepoUuid() {
        return (UUID) get(4);
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
    // Record5 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<UUID, String, String, UUID, UUID> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<UUID, String, String, UUID, UUID> valuesRow() {
        return (Row5) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UUID> field1() {
        return ResourceDetailLookupTable.RESOURCE_DETAIL_LOOKUP.UUID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return ResourceDetailLookupTable.RESOURCE_DETAIL_LOOKUP.PROJECT_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return ResourceDetailLookupTable.RESOURCE_DETAIL_LOOKUP.REPO_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UUID> field4() {
        return ResourceDetailLookupTable.RESOURCE_DETAIL_LOOKUP.PROJECT_UUID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UUID> field5() {
        return ResourceDetailLookupTable.RESOURCE_DETAIL_LOOKUP.REPO_UUID;
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
    public String value3() {
        return getRepoName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UUID value4() {
        return getProjectUuid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UUID value5() {
        return getRepoUuid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResourceDetailLookupRecord value1(UUID value) {
        setUuid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResourceDetailLookupRecord value2(String value) {
        setProjectName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResourceDetailLookupRecord value3(String value) {
        setRepoName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResourceDetailLookupRecord value4(UUID value) {
        setProjectUuid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResourceDetailLookupRecord value5(UUID value) {
        setRepoUuid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResourceDetailLookupRecord values(UUID value1, String value2, String value3, UUID value4, UUID value5) {
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
     * Create a detached ResourceDetailLookupRecord
     */
    public ResourceDetailLookupRecord() {
        super(ResourceDetailLookupTable.RESOURCE_DETAIL_LOOKUP);
    }

    /**
     * Create a detached, initialised ResourceDetailLookupRecord
     */
    public ResourceDetailLookupRecord(UUID uuid, String projectName, String repoName, UUID projectUuid, UUID repoUuid) {
        super(ResourceDetailLookupTable.RESOURCE_DETAIL_LOOKUP);

        set(0, uuid);
        set(1, projectName);
        set(2, repoName);
        set(3, projectUuid);
        set(4, repoUuid);
    }
}
