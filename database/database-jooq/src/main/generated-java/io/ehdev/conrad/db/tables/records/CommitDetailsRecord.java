/**
 * This class is generated by jOOQ
 */
package io.ehdev.conrad.db.tables.records;


import io.ehdev.conrad.db.tables.CommitDetailsTable;

import java.time.Instant;
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
import org.jooq.Record6;
import org.jooq.Row6;
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
@Table(name = "commit_details", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"commit_id", "repo_details_uuid"}),
    @UniqueConstraint(columnNames = {"version", "repo_details_uuid"})
})
public class CommitDetailsRecord extends UpdatableRecordImpl<CommitDetailsRecord> implements Record6<UUID, UUID, UUID, String, String, Instant> {

    private static final long serialVersionUID = -761142455;

    /**
     * Setter for <code>public.commit_details.uuid</code>.
     */
    public void setUuid(UUID value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.commit_details.uuid</code>.
     */
    @Id
    @Column(name = "uuid", unique = true, nullable = false)
    public UUID getUuid() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>public.commit_details.repo_details_uuid</code>.
     */
    public void setRepoDetailsUuid(UUID value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.commit_details.repo_details_uuid</code>.
     */
    @Column(name = "repo_details_uuid", nullable = false)
    @NotNull
    public UUID getRepoDetailsUuid() {
        return (UUID) get(1);
    }

    /**
     * Setter for <code>public.commit_details.parent_commit_uuid</code>.
     */
    public void setParentCommitUuid(UUID value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.commit_details.parent_commit_uuid</code>.
     */
    @Column(name = "parent_commit_uuid")
    public UUID getParentCommitUuid() {
        return (UUID) get(2);
    }

    /**
     * Setter for <code>public.commit_details.commit_id</code>.
     */
    public void setCommitId(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.commit_details.commit_id</code>.
     */
    @Column(name = "commit_id", nullable = false, length = 40)
    @NotNull
    @Size(max = 40)
    public String getCommitId() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public.commit_details.version</code>.
     */
    public void setVersion(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.commit_details.version</code>.
     */
    @Column(name = "version", nullable = false, length = 120)
    @NotNull
    @Size(max = 120)
    public String getVersion() {
        return (String) get(4);
    }

    /**
     * Setter for <code>public.commit_details.created_at</code>.
     */
    public void setCreatedAt(Instant value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.commit_details.created_at</code>.
     */
    @Column(name = "created_at", nullable = false)
    @NotNull
    public Instant getCreatedAt() {
        return (Instant) get(5);
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
    // Record6 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<UUID, UUID, UUID, String, String, Instant> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<UUID, UUID, UUID, String, String, Instant> valuesRow() {
        return (Row6) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UUID> field1() {
        return CommitDetailsTable.COMMIT_DETAILS.UUID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UUID> field2() {
        return CommitDetailsTable.COMMIT_DETAILS.REPO_DETAILS_UUID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UUID> field3() {
        return CommitDetailsTable.COMMIT_DETAILS.PARENT_COMMIT_UUID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return CommitDetailsTable.COMMIT_DETAILS.COMMIT_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return CommitDetailsTable.COMMIT_DETAILS.VERSION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Instant> field6() {
        return CommitDetailsTable.COMMIT_DETAILS.CREATED_AT;
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
    public UUID value2() {
        return getRepoDetailsUuid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UUID value3() {
        return getParentCommitUuid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getCommitId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getVersion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Instant value6() {
        return getCreatedAt();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommitDetailsRecord value1(UUID value) {
        setUuid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommitDetailsRecord value2(UUID value) {
        setRepoDetailsUuid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommitDetailsRecord value3(UUID value) {
        setParentCommitUuid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommitDetailsRecord value4(String value) {
        setCommitId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommitDetailsRecord value5(String value) {
        setVersion(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommitDetailsRecord value6(Instant value) {
        setCreatedAt(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommitDetailsRecord values(UUID value1, UUID value2, UUID value3, String value4, String value5, Instant value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached CommitDetailsRecord
     */
    public CommitDetailsRecord() {
        super(CommitDetailsTable.COMMIT_DETAILS);
    }

    /**
     * Create a detached, initialised CommitDetailsRecord
     */
    public CommitDetailsRecord(UUID uuid, UUID repoDetailsUuid, UUID parentCommitUuid, String commitId, String version, Instant createdAt) {
        super(CommitDetailsTable.COMMIT_DETAILS);

        set(0, uuid);
        set(1, repoDetailsUuid);
        set(2, parentCommitUuid);
        set(3, commitId);
        set(4, version);
        set(5, createdAt);
    }
}
