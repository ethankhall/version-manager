/**
 * This class is generated by jOOQ
 */
package io.ehdev.conrad.db;


import javax.annotation.Generated;

import org.jooq.Sequence;
import org.jooq.impl.SequenceImpl;


/**
 * Convenience access to all sequences in public
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Sequences {

    /**
     * The sequence <code>public.acl_class_id_seq</code>
     */
    public static final Sequence<Long> ACL_CLASS_ID_SEQ = new SequenceImpl<Long>("acl_class_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.acl_entry_id_seq</code>
     */
    public static final Sequence<Long> ACL_ENTRY_ID_SEQ = new SequenceImpl<Long>("acl_entry_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.acl_object_identity_id_seq</code>
     */
    public static final Sequence<Long> ACL_OBJECT_IDENTITY_ID_SEQ = new SequenceImpl<Long>("acl_object_identity_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.acl_sid_id_seq</code>
     */
    public static final Sequence<Long> ACL_SID_ID_SEQ = new SequenceImpl<Long>("acl_sid_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.token_auth_security_id_seq</code>
     */
    public static final Sequence<Long> TOKEN_AUTH_SECURITY_ID_SEQ = new SequenceImpl<Long>("token_auth_security_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));
}