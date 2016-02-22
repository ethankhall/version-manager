package io.ehdev.conrad.database.api.internal;

import io.ehdev.conrad.database.api.UserManagementApi;
import io.ehdev.conrad.database.impl.ModelConversionUtility;
import io.ehdev.conrad.database.model.permission.UserApiAuthentication;
import io.ehdev.conrad.db.Tables;
import io.ehdev.conrad.db.tables.pojos.UserDetails;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultUserManagementApi implements UserManagementApi {

    private final DSLContext dslContext;

    @Autowired
    public DefaultUserManagementApi(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public UserApiAuthentication createUser(String userName, String name, String email) {
        UserDetails userDetails = dslContext
            .insertInto(Tables.USER_DETAILS, Tables.USER_DETAILS.USER_NAME, Tables.USER_DETAILS.NAME, Tables.USER_DETAILS.EMAIL_ADDRESS)
            .values(userName, name, email)
            .returning(Tables.USER_DETAILS.fields())
            .fetchOne()
            .into(UserDetails.class);

        return ModelConversionUtility.toApiModel(userDetails);
    }
}
