/**
 * This class is generated by jOOQ
 */
package tech.crom.db.tables.records;


import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;
import tech.crom.db.tables.RepositoryTokensTable;

import javax.annotation.Generated;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;


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
@Table(name = "repository_tokens", schema = "version_manager_test")
public class RepositoryTokensRecord extends UpdatableRecordImpl<RepositoryTokensRecord> implements Record5<Long, Instant, Instant, Boolean, Long> {

    private static final long serialVersionUID = -2013671517;

    /**
     * Setter for <code>version_manager_test.repository_tokens.repository_tokens_id</code>.
     */
    public void setRepositoryTokensId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>version_manager_test.repository_tokens.repository_tokens_id</code>.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "repository_tokens_id", unique = true, nullable = false, precision = 19)
    @NotNull
    public Long getRepositoryTokensId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>version_manager_test.repository_tokens.created_at</code>.
     */
    public void setCreatedAt(Instant value) {
        set(1, value);
    }

    /**
     * Getter for <code>version_manager_test.repository_tokens.created_at</code>.
     */
    @Column(name = "created_at", nullable = false)
    public Instant getCreatedAt() {
        return (Instant) get(1);
    }

    /**
     * Setter for <code>version_manager_test.repository_tokens.expires_at</code>.
     */
    public void setExpiresAt(Instant value) {
        set(2, value);
    }

    /**
     * Getter for <code>version_manager_test.repository_tokens.expires_at</code>.
     */
    @Column(name = "expires_at", nullable = false)
    public Instant getExpiresAt() {
        return (Instant) get(2);
    }

    /**
     * Setter for <code>version_manager_test.repository_tokens.valid</code>.
     */
    public void setValid(Boolean value) {
        set(3, value);
    }

    /**
     * Getter for <code>version_manager_test.repository_tokens.valid</code>.
     */
    @Column(name = "valid")
    public Boolean getValid() {
        return (Boolean) get(3);
    }

    /**
     * Setter for <code>version_manager_test.repository_tokens.repo_id</code>.
     */
    public void setRepoId(Long value) {
        set(4, value);
    }

    /**
     * Getter for <code>version_manager_test.repository_tokens.repo_id</code>.
     */
    @Column(name = "repo_id", nullable = false, precision = 19)
    @NotNull
    public Long getRepoId() {
        return (Long) get(4);
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
    public Row5<Long, Instant, Instant, Boolean, Long> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<Long, Instant, Instant, Boolean, Long> valuesRow() {
        return (Row5) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return RepositoryTokensTable.REPOSITORY_TOKENS.REPOSITORY_TOKENS_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Instant> field2() {
        return RepositoryTokensTable.REPOSITORY_TOKENS.CREATED_AT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Instant> field3() {
        return RepositoryTokensTable.REPOSITORY_TOKENS.EXPIRES_AT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Boolean> field4() {
        return RepositoryTokensTable.REPOSITORY_TOKENS.VALID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field5() {
        return RepositoryTokensTable.REPOSITORY_TOKENS.REPO_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value1() {
        return getRepositoryTokensId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Instant value2() {
        return getCreatedAt();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Instant value3() {
        return getExpiresAt();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean value4() {
        return getValid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value5() {
        return getRepoId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RepositoryTokensRecord value1(Long value) {
        setRepositoryTokensId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RepositoryTokensRecord value2(Instant value) {
        setCreatedAt(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RepositoryTokensRecord value3(Instant value) {
        setExpiresAt(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RepositoryTokensRecord value4(Boolean value) {
        setValid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RepositoryTokensRecord value5(Long value) {
        setRepoId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RepositoryTokensRecord values(Long value1, Instant value2, Instant value3, Boolean value4, Long value5) {
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
     * Create a detached RepositoryTokensRecord
     */
    public RepositoryTokensRecord() {
        super(RepositoryTokensTable.REPOSITORY_TOKENS);
    }

    /**
     * Create a detached, initialised RepositoryTokensRecord
     */
    public RepositoryTokensRecord(Long repositoryTokensId, Instant createdAt, Instant expiresAt, Boolean valid, Long repoId) {
        super(RepositoryTokensTable.REPOSITORY_TOKENS);

        set(0, repositoryTokensId);
        set(1, createdAt);
        set(2, expiresAt);
        set(3, valid);
        set(4, repoId);
    }
}
