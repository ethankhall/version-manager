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
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;

import tech.crom.db.tables.SecurityIdSeqTable;


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
@Table(name = "security_id_seq", schema = "version_manager_test")
public class SecurityIdSeqRecord extends UpdatableRecordImpl<SecurityIdSeqRecord> implements Record2<Long, String> {

    private static final long serialVersionUID = 1492484636;

    /**
     * Setter for <code>version_manager_test.security_id_seq.security_id</code>.
     */
    public void setSecurityId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>version_manager_test.security_id_seq.security_id</code>.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "security_id", unique = true, nullable = false, precision = 19)
    @NotNull
    public Long getSecurityId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>version_manager_test.security_id_seq.type</code>.
     */
    public void setType(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>version_manager_test.security_id_seq.type</code>.
     */
    @Column(name = "type", length = 10)
    @Size(max = 10)
    public String getType() {
        return (String) get(1);
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
    // Record2 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row2<Long, String> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row2<Long, String> valuesRow() {
        return (Row2) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return SecurityIdSeqTable.SECURITY_ID_SEQ.SECURITY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return SecurityIdSeqTable.SECURITY_ID_SEQ.TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value1() {
        return getSecurityId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SecurityIdSeqRecord value1(Long value) {
        setSecurityId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SecurityIdSeqRecord value2(String value) {
        setType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SecurityIdSeqRecord values(Long value1, String value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached SecurityIdSeqRecord
     */
    public SecurityIdSeqRecord() {
        super(SecurityIdSeqTable.SECURITY_ID_SEQ);
    }

    /**
     * Create a detached, initialised SecurityIdSeqRecord
     */
    public SecurityIdSeqRecord(Long securityId, String type) {
        super(SecurityIdSeqTable.SECURITY_ID_SEQ);

        set(0, securityId);
        set(1, type);
    }
}
