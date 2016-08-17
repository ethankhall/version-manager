/**
 * This class is generated by jOOQ
 */
package io.ehdev.conrad.db.tables.records;


import io.ehdev.conrad.db.tables.SsUserconnectionTable;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jooq.Field;
import org.jooq.Record11;
import org.jooq.Record3;
import org.jooq.Row11;
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
@Table(name = "ss_userconnection", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"userid", "providerid", "provideruserid"})
})
public class SsUserconnectionRecord extends UpdatableRecordImpl<SsUserconnectionRecord> implements Record11<String, String, String, Integer, String, String, String, String, String, String, Long> {

    private static final long serialVersionUID = -1132935315;

    /**
     * Setter for <code>public.ss_userconnection.userid</code>.
     */
    public void setUserid(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.ss_userconnection.userid</code>.
     */
    @Column(name = "userid", nullable = false, length = 255)
    @NotNull
    @Size(max = 255)
    public String getUserid() {
        return (String) get(0);
    }

    /**
     * Setter for <code>public.ss_userconnection.providerid</code>.
     */
    public void setProviderid(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.ss_userconnection.providerid</code>.
     */
    @Column(name = "providerid", nullable = false, length = 255)
    @NotNull
    @Size(max = 255)
    public String getProviderid() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.ss_userconnection.provideruserid</code>.
     */
    public void setProvideruserid(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.ss_userconnection.provideruserid</code>.
     */
    @Column(name = "provideruserid", nullable = false, length = 255)
    @NotNull
    @Size(max = 255)
    public String getProvideruserid() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.ss_userconnection.rank</code>.
     */
    public void setRank(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.ss_userconnection.rank</code>.
     */
    @Column(name = "rank", nullable = false, precision = 32)
    @NotNull
    public Integer getRank() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>public.ss_userconnection.displayname</code>.
     */
    public void setDisplayname(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.ss_userconnection.displayname</code>.
     */
    @Column(name = "displayname", length = 255)
    @Size(max = 255)
    public String getDisplayname() {
        return (String) get(4);
    }

    /**
     * Setter for <code>public.ss_userconnection.profileurl</code>.
     */
    public void setProfileurl(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.ss_userconnection.profileurl</code>.
     */
    @Column(name = "profileurl", length = 512)
    @Size(max = 512)
    public String getProfileurl() {
        return (String) get(5);
    }

    /**
     * Setter for <code>public.ss_userconnection.imageurl</code>.
     */
    public void setImageurl(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.ss_userconnection.imageurl</code>.
     */
    @Column(name = "imageurl", length = 512)
    @Size(max = 512)
    public String getImageurl() {
        return (String) get(6);
    }

    /**
     * Setter for <code>public.ss_userconnection.accesstoken</code>.
     */
    public void setAccesstoken(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>public.ss_userconnection.accesstoken</code>.
     */
    @Column(name = "accesstoken", nullable = false, length = 512)
    @NotNull
    @Size(max = 512)
    public String getAccesstoken() {
        return (String) get(7);
    }

    /**
     * Setter for <code>public.ss_userconnection.secret</code>.
     */
    public void setSecret(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>public.ss_userconnection.secret</code>.
     */
    @Column(name = "secret", length = 512)
    @Size(max = 512)
    public String getSecret() {
        return (String) get(8);
    }

    /**
     * Setter for <code>public.ss_userconnection.refreshtoken</code>.
     */
    public void setRefreshtoken(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>public.ss_userconnection.refreshtoken</code>.
     */
    @Column(name = "refreshtoken", length = 512)
    @Size(max = 512)
    public String getRefreshtoken() {
        return (String) get(9);
    }

    /**
     * Setter for <code>public.ss_userconnection.expiretime</code>.
     */
    public void setExpiretime(Long value) {
        set(10, value);
    }

    /**
     * Getter for <code>public.ss_userconnection.expiretime</code>.
     */
    @Column(name = "expiretime", precision = 64)
    public Long getExpiretime() {
        return (Long) get(10);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record3<String, String, String> key() {
        return (Record3) super.key();
    }

    // -------------------------------------------------------------------------
    // Record11 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row11<String, String, String, Integer, String, String, String, String, String, String, Long> fieldsRow() {
        return (Row11) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row11<String, String, String, Integer, String, String, String, String, String, String, Long> valuesRow() {
        return (Row11) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return SsUserconnectionTable.SS_USERCONNECTION.USERID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return SsUserconnectionTable.SS_USERCONNECTION.PROVIDERID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return SsUserconnectionTable.SS_USERCONNECTION.PROVIDERUSERID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return SsUserconnectionTable.SS_USERCONNECTION.RANK;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return SsUserconnectionTable.SS_USERCONNECTION.DISPLAYNAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return SsUserconnectionTable.SS_USERCONNECTION.PROFILEURL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return SsUserconnectionTable.SS_USERCONNECTION.IMAGEURL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return SsUserconnectionTable.SS_USERCONNECTION.ACCESSTOKEN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field9() {
        return SsUserconnectionTable.SS_USERCONNECTION.SECRET;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field10() {
        return SsUserconnectionTable.SS_USERCONNECTION.REFRESHTOKEN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field11() {
        return SsUserconnectionTable.SS_USERCONNECTION.EXPIRETIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value1() {
        return getUserid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getProviderid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getProvideruserid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getRank();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getDisplayname();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getProfileurl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getImageurl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value8() {
        return getAccesstoken();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value9() {
        return getSecret();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value10() {
        return getRefreshtoken();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value11() {
        return getExpiretime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SsUserconnectionRecord value1(String value) {
        setUserid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SsUserconnectionRecord value2(String value) {
        setProviderid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SsUserconnectionRecord value3(String value) {
        setProvideruserid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SsUserconnectionRecord value4(Integer value) {
        setRank(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SsUserconnectionRecord value5(String value) {
        setDisplayname(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SsUserconnectionRecord value6(String value) {
        setProfileurl(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SsUserconnectionRecord value7(String value) {
        setImageurl(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SsUserconnectionRecord value8(String value) {
        setAccesstoken(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SsUserconnectionRecord value9(String value) {
        setSecret(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SsUserconnectionRecord value10(String value) {
        setRefreshtoken(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SsUserconnectionRecord value11(Long value) {
        setExpiretime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SsUserconnectionRecord values(String value1, String value2, String value3, Integer value4, String value5, String value6, String value7, String value8, String value9, String value10, Long value11) {
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
        value11(value11);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached SsUserconnectionRecord
     */
    public SsUserconnectionRecord() {
        super(SsUserconnectionTable.SS_USERCONNECTION);
    }

    /**
     * Create a detached, initialised SsUserconnectionRecord
     */
    public SsUserconnectionRecord(String userid, String providerid, String provideruserid, Integer rank, String displayname, String profileurl, String imageurl, String accesstoken, String secret, String refreshtoken, Long expiretime) {
        super(SsUserconnectionTable.SS_USERCONNECTION);

        set(0, userid);
        set(1, providerid);
        set(2, provideruserid);
        set(3, rank);
        set(4, displayname);
        set(5, profileurl);
        set(6, imageurl);
        set(7, accesstoken);
        set(8, secret);
        set(9, refreshtoken);
        set(10, expiretime);
    }
}
