/**
 * This class is generated by jOOQ
 */
package io.ehdev.conrad.db.tables.records;


import io.ehdev.conrad.db.tables.AclClassTable;

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
@Table(name = "acl_class", schema = "public")
public class AclClassRecord extends UpdatableRecordImpl<AclClassRecord> implements Record2<Long, String> {

    private static final long serialVersionUID = -1967805766;

    /**
     * Setter for <code>public.acl_class.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.acl_class.id</code>.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, precision = 64)
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.acl_class.class</code>.
     */
    public void setClass_(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.acl_class.class</code>.
     */
    @Column(name = "class", unique = true, nullable = false, length = 100)
    @NotNull
    @Size(max = 100)
    public String getClass_() {
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
        return AclClassTable.ACL_CLASS.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return AclClassTable.ACL_CLASS.CLASS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getClass_();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AclClassRecord value1(Long value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AclClassRecord value2(String value) {
        setClass_(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AclClassRecord values(Long value1, String value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached AclClassRecord
     */
    public AclClassRecord() {
        super(AclClassTable.ACL_CLASS);
    }

    /**
     * Create a detached, initialised AclClassRecord
     */
    public AclClassRecord(Long id, String class_) {
        super(AclClassTable.ACL_CLASS);

        set(0, id);
        set(1, class_);
    }
}
