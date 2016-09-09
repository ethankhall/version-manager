/**
 * This class is generated by jOOQ
 */
package io.ehdev.conrad.db.tables.records;


import io.ehdev.conrad.db.tables.AclEntryTable;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record8;
import org.jooq.Row8;
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
@Table(name = "acl_entry", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"acl_object_identity", "ace_order"})
})
public class AclEntryRecord extends UpdatableRecordImpl<AclEntryRecord> implements Record8<Long, Long, Integer, Long, Integer, Boolean, Boolean, Boolean> {

    private static final long serialVersionUID = 981313088;

    /**
     * Setter for <code>public.acl_entry.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.acl_entry.id</code>.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, precision = 64)
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.acl_entry.acl_object_identity</code>.
     */
    public void setAclObjectIdentity(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.acl_entry.acl_object_identity</code>.
     */
    @Column(name = "acl_object_identity", nullable = false, precision = 64)
    @NotNull
    public Long getAclObjectIdentity() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>public.acl_entry.ace_order</code>.
     */
    public void setAceOrder(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.acl_entry.ace_order</code>.
     */
    @Column(name = "ace_order", nullable = false, precision = 32)
    @NotNull
    public Integer getAceOrder() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>public.acl_entry.sid</code>.
     */
    public void setSid(Long value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.acl_entry.sid</code>.
     */
    @Column(name = "sid", nullable = false, precision = 64)
    @NotNull
    public Long getSid() {
        return (Long) get(3);
    }

    /**
     * Setter for <code>public.acl_entry.mask</code>.
     */
    public void setMask(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.acl_entry.mask</code>.
     */
    @Column(name = "mask", nullable = false, precision = 32)
    @NotNull
    public Integer getMask() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>public.acl_entry.granting</code>.
     */
    public void setGranting(Boolean value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.acl_entry.granting</code>.
     */
    @Column(name = "granting", nullable = false)
    @NotNull
    public Boolean getGranting() {
        return (Boolean) get(5);
    }

    /**
     * Setter for <code>public.acl_entry.audit_success</code>.
     */
    public void setAuditSuccess(Boolean value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.acl_entry.audit_success</code>.
     */
    @Column(name = "audit_success", nullable = false)
    @NotNull
    public Boolean getAuditSuccess() {
        return (Boolean) get(6);
    }

    /**
     * Setter for <code>public.acl_entry.audit_failure</code>.
     */
    public void setAuditFailure(Boolean value) {
        set(7, value);
    }

    /**
     * Getter for <code>public.acl_entry.audit_failure</code>.
     */
    @Column(name = "audit_failure", nullable = false)
    @NotNull
    public Boolean getAuditFailure() {
        return (Boolean) get(7);
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
    // Record8 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<Long, Long, Integer, Long, Integer, Boolean, Boolean, Boolean> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<Long, Long, Integer, Long, Integer, Boolean, Boolean, Boolean> valuesRow() {
        return (Row8) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return AclEntryTable.ACL_ENTRY.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field2() {
        return AclEntryTable.ACL_ENTRY.ACL_OBJECT_IDENTITY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return AclEntryTable.ACL_ENTRY.ACE_ORDER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field4() {
        return AclEntryTable.ACL_ENTRY.SID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return AclEntryTable.ACL_ENTRY.MASK;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Boolean> field6() {
        return AclEntryTable.ACL_ENTRY.GRANTING;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Boolean> field7() {
        return AclEntryTable.ACL_ENTRY.AUDIT_SUCCESS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Boolean> field8() {
        return AclEntryTable.ACL_ENTRY.AUDIT_FAILURE;
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
    public Long value2() {
        return getAclObjectIdentity();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getAceOrder();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value4() {
        return getSid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getMask();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean value6() {
        return getGranting();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean value7() {
        return getAuditSuccess();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean value8() {
        return getAuditFailure();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AclEntryRecord value1(Long value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AclEntryRecord value2(Long value) {
        setAclObjectIdentity(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AclEntryRecord value3(Integer value) {
        setAceOrder(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AclEntryRecord value4(Long value) {
        setSid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AclEntryRecord value5(Integer value) {
        setMask(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AclEntryRecord value6(Boolean value) {
        setGranting(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AclEntryRecord value7(Boolean value) {
        setAuditSuccess(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AclEntryRecord value8(Boolean value) {
        setAuditFailure(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AclEntryRecord values(Long value1, Long value2, Integer value3, Long value4, Integer value5, Boolean value6, Boolean value7, Boolean value8) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached AclEntryRecord
     */
    public AclEntryRecord() {
        super(AclEntryTable.ACL_ENTRY);
    }

    /**
     * Create a detached, initialised AclEntryRecord
     */
    public AclEntryRecord(Long id, Long aclObjectIdentity, Integer aceOrder, Long sid, Integer mask, Boolean granting, Boolean auditSuccess, Boolean auditFailure) {
        super(AclEntryTable.ACL_ENTRY);

        set(0, id);
        set(1, aclObjectIdentity);
        set(2, aceOrder);
        set(3, sid);
        set(4, mask);
        set(5, granting);
        set(6, auditSuccess);
        set(7, auditFailure);
    }
}
