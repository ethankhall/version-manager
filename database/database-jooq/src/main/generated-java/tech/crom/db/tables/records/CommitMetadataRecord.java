/*
 * This file is generated by jOOQ.
*/
package tech.crom.db.tables.records;


import java.time.Instant;

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
import org.jooq.Record10;
import org.jooq.Row10;
import org.jooq.impl.UpdatableRecordImpl;

import tech.crom.db.tables.CommitMetadataTable;


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
@Table(name = "commit_metadata", schema = "version_manager_test")
public class CommitMetadataRecord extends UpdatableRecordImpl<CommitMetadataRecord> implements Record10<Long, Long, Long, Long, String, String, Long, String, Instant, Instant> {

    private static final long serialVersionUID = 971127664;

    /**
     * Setter for <code>version_manager_test.commit_metadata.commit_metadata_id</code>.
     */
    public void setCommitMetadataId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>version_manager_test.commit_metadata.commit_metadata_id</code>.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commit_metadata_id", unique = true, nullable = false, precision = 19)
    @NotNull
    public Long getCommitMetadataId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>version_manager_test.commit_metadata.commit_id</code>.
     */
    public void setCommitId(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>version_manager_test.commit_metadata.commit_id</code>.
     */
    @Column(name = "commit_id", nullable = false, precision = 19)
    @NotNull
    public Long getCommitId() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>version_manager_test.commit_metadata.project_id</code>.
     */
    public void setProjectId(Long value) {
        set(2, value);
    }

    /**
     * Getter for <code>version_manager_test.commit_metadata.project_id</code>.
     */
    @Column(name = "project_id", nullable = false, precision = 19)
    @NotNull
    public Long getProjectId() {
        return (Long) get(2);
    }

    /**
     * Setter for <code>version_manager_test.commit_metadata.repo_id</code>.
     */
    public void setRepoId(Long value) {
        set(3, value);
    }

    /**
     * Getter for <code>version_manager_test.commit_metadata.repo_id</code>.
     */
    @Column(name = "repo_id", nullable = false, precision = 19)
    @NotNull
    public Long getRepoId() {
        return (Long) get(3);
    }

    /**
     * Setter for <code>version_manager_test.commit_metadata.name</code>.
     */
    public void setName(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>version_manager_test.commit_metadata.name</code>.
     */
    @Column(name = "name", nullable = false, length = 128)
    @NotNull
    @Size(max = 128)
    public String getName() {
        return (String) get(4);
    }

    /**
     * Setter for <code>version_manager_test.commit_metadata.uri</code>.
     */
    public void setUri(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>version_manager_test.commit_metadata.uri</code>.
     */
    @Column(name = "uri", nullable = false, length = 65535)
    @NotNull
    @Size(max = 65535)
    public String getUri() {
        return (String) get(5);
    }

    /**
     * Setter for <code>version_manager_test.commit_metadata.size</code>.
     */
    public void setSize(Long value) {
        set(6, value);
    }

    /**
     * Getter for <code>version_manager_test.commit_metadata.size</code>.
     */
    @Column(name = "size", nullable = false, precision = 19)
    @NotNull
    public Long getSize() {
        return (Long) get(6);
    }

    /**
     * Setter for <code>version_manager_test.commit_metadata.content_type</code>.
     */
    public void setContentType(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>version_manager_test.commit_metadata.content_type</code>.
     */
    @Column(name = "content_type", nullable = false, length = 64)
    @NotNull
    @Size(max = 64)
    public String getContentType() {
        return (String) get(7);
    }

    /**
     * Setter for <code>version_manager_test.commit_metadata.created_at</code>.
     */
    public void setCreatedAt(Instant value) {
        set(8, value);
    }

    /**
     * Getter for <code>version_manager_test.commit_metadata.created_at</code>.
     */
    @Column(name = "created_at", nullable = false)
    public Instant getCreatedAt() {
        return (Instant) get(8);
    }

    /**
     * Setter for <code>version_manager_test.commit_metadata.updated_at</code>.
     */
    public void setUpdatedAt(Instant value) {
        set(9, value);
    }

    /**
     * Getter for <code>version_manager_test.commit_metadata.updated_at</code>.
     */
    @Column(name = "updated_at", nullable = false)
    public Instant getUpdatedAt() {
        return (Instant) get(9);
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
    // Record10 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row10<Long, Long, Long, Long, String, String, Long, String, Instant, Instant> fieldsRow() {
        return (Row10) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row10<Long, Long, Long, Long, String, String, Long, String, Instant, Instant> valuesRow() {
        return (Row10) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return CommitMetadataTable.COMMIT_METADATA.COMMIT_METADATA_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field2() {
        return CommitMetadataTable.COMMIT_METADATA.COMMIT_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field3() {
        return CommitMetadataTable.COMMIT_METADATA.PROJECT_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field4() {
        return CommitMetadataTable.COMMIT_METADATA.REPO_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return CommitMetadataTable.COMMIT_METADATA.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return CommitMetadataTable.COMMIT_METADATA.URI;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field7() {
        return CommitMetadataTable.COMMIT_METADATA.SIZE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return CommitMetadataTable.COMMIT_METADATA.CONTENT_TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Instant> field9() {
        return CommitMetadataTable.COMMIT_METADATA.CREATED_AT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Instant> field10() {
        return CommitMetadataTable.COMMIT_METADATA.UPDATED_AT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value1() {
        return getCommitMetadataId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value2() {
        return getCommitId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value3() {
        return getProjectId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value4() {
        return getRepoId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getUri();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value7() {
        return getSize();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value8() {
        return getContentType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Instant value9() {
        return getCreatedAt();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Instant value10() {
        return getUpdatedAt();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommitMetadataRecord value1(Long value) {
        setCommitMetadataId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommitMetadataRecord value2(Long value) {
        setCommitId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommitMetadataRecord value3(Long value) {
        setProjectId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommitMetadataRecord value4(Long value) {
        setRepoId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommitMetadataRecord value5(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommitMetadataRecord value6(String value) {
        setUri(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommitMetadataRecord value7(Long value) {
        setSize(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommitMetadataRecord value8(String value) {
        setContentType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommitMetadataRecord value9(Instant value) {
        setCreatedAt(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommitMetadataRecord value10(Instant value) {
        setUpdatedAt(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommitMetadataRecord values(Long value1, Long value2, Long value3, Long value4, String value5, String value6, Long value7, String value8, Instant value9, Instant value10) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached CommitMetadataRecord
     */
    public CommitMetadataRecord() {
        super(CommitMetadataTable.COMMIT_METADATA);
    }

    /**
     * Create a detached, initialised CommitMetadataRecord
     */
    public CommitMetadataRecord(Long commitMetadataId, Long commitId, Long projectId, Long repoId, String name, String uri, Long size, String contentType, Instant createdAt, Instant updatedAt) {
        super(CommitMetadataTable.COMMIT_METADATA);

        set(0, commitMetadataId);
        set(1, commitId);
        set(2, projectId);
        set(3, repoId);
        set(4, name);
        set(5, uri);
        set(6, size);
        set(7, contentType);
        set(8, createdAt);
        set(9, updatedAt);
    }
}
