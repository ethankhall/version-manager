/**
 * This class is generated by jOOQ
 */
package io.ehdev.conrad.db.routines;


import io.ehdev.conrad.db.Public;

import java.util.UUID;

import javax.annotation.Generated;

import org.jooq.Parameter;
import org.jooq.impl.AbstractRoutine;


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
public class UuidNsUrl extends AbstractRoutine<UUID> {

    private static final long serialVersionUID = -997010249;

    /**
     * The parameter <code>public.uuid_ns_url.RETURN_VALUE</code>.
     */
    public static final Parameter<UUID> RETURN_VALUE = createParameter("RETURN_VALUE", org.jooq.impl.SQLDataType.UUID, false, false);

    /**
     * Create a new routine call instance
     */
    public UuidNsUrl() {
        super("uuid_ns_url", Public.PUBLIC, org.jooq.impl.SQLDataType.UUID);

        setReturnParameter(RETURN_VALUE);
    }
}
