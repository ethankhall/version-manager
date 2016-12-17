/**
 * This class is generated by jOOQ
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
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;

import tech.crom.db.tables.CommitDetailsTable;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
@Entity
@Table(name = "commit_details", schema = "version_manager_test")
public class CommitDetailsRecord extends UpdatableRecordImpl<CommitDetailsRecord> implements Record6<Long, Long, Long, String, String, Instant> {

    private static final long serialVersionUID = 840092032;

    /**
     * Setter for <code>version_manager_test.commit_details.commit_details_id</code>.
     */
    public void setCommitDetailsId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>version_manager_test.commit_details.commit_details_id</code>.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commit_details_id", unique = true, nullable = false, precision = 19)
    @NotNull
    public Long getCommitDetailsId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>version_manager_test.commit_details.repo_details_id</code>.
     */
    public void setRepoDetailsId(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>version_manager_test.commit_details.repo_details_id</code>.
     */
    @Column(name = "repo_details_id", nullable = false, precision = 19)
    @NotNull
    public Long getRepoDetailsId() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>version_manager_test.commit_details.parent_commit_id</code>.
     */
    public void setParentCommitId(Long value) {
        set(2, value);
    }

    /**
     * Getter for <code>version_manager_test.commit_details.parent_commit_id</code>.
     */
    @Column(name = "parent_commit_id", precision = 19)
    public Long getParentCommitId() {
        return (Long) get(2);
    }

    /**
     * Setter for <code>version_manager_test.commit_details.commit_id</code>.
     */
    public void setCommitId(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>version_manager_test.commit_details.commit_id</code>.
     */
    @Column(name = "commit_id", nullable = false, length = 40)
    @NotNull
    @Size(max = 40)
    public String getCommitId() {
        return (String) get(3);
    }

    /**
     * Setter for <code>version_manager_test.commit_details.version</code>.
     */
    public void setVersion(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>version_manager_test.commit_details.version</code>.
     */
    @Column(name = "version", nullable = false, length = 120)
    @NotNull
    @Size(max = 120)
    public String getVersion() {
        return (String) get(4);
    }

    /**
     * Setter for <code>version_manager_test.commit_details.created_at</code>.
     */
    public void setCreatedAt(Instant value) {
        set(5, value);
    }

    /**
     * Getter for <code>version_manager_test.commit_details.created_at</code>.
     */
    @Column(name = "created_at", nullable = false)
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
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record6 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Long, Long, Long, String, String, Instant> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Long, Long, Long, String, String, Instant> valuesRow() {
        return (Row6) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return CommitDetailsTable.COMMIT_DETAILS.COMMIT_DETAILS_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field2() {
        return CommitDetailsTable.COMMIT_DETAILS.REPO_DETAILS_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field3() {
        return CommitDetailsTable.COMMIT_DETAILS.PARENT_COMMIT_ID;
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
    public Long value1() {
        return getCommitDetailsId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value2() {
        return getRepoDetailsId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value3() {
        return getParentCommitId();
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
    public CommitDetailsRecord value1(Long value) {
        setCommitDetailsId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommitDetailsRecord value2(Long value) {
        setRepoDetailsId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommitDetailsRecord value3(Long value) {
        setParentCommitId(value);
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
    public CommitDetailsRecord values(Long value1, Long value2, Long value3, String value4, String value5, Instant value6) {
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
    public CommitDetailsRecord(Long commitDetailsId, Long repoDetailsId, Long parentCommitId, String commitId, String version, Instant createdAt) {
        super(CommitDetailsTable.COMMIT_DETAILS);

        set(0, commitDetailsId);
        set(1, repoDetailsId);
        set(2, parentCommitId);
        set(3, commitId);
        set(4, version);
        set(5, createdAt);
    }
}
