package io.ehdev.conrad.database.api.internal;

import io.ehdev.conrad.database.api.UserManagementApi;
import io.ehdev.conrad.database.impl.ModelConversionUtility;
import io.ehdev.conrad.database.impl.user.BaseUserModel;
import io.ehdev.conrad.database.impl.user.BaseUserRepository;
import io.ehdev.conrad.database.model.user.ApiUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultUserManagementApi implements UserManagementApi {

    final private BaseUserRepository baseUserRepository;

    @Autowired
    public DefaultUserManagementApi(BaseUserRepository baseUserRepository) {
        this.baseUserRepository = baseUserRepository;
    }

    public BaseUserModel createInternalUser(String name, String email) {
        return baseUserRepository.save(new BaseUserModel(name, email));
    }

    @Override
    public ApiUser createUser(String name, String email) {
        BaseUserModel user = createInternalUser(name, email);
        return ModelConversionUtility.toApiModel(user);
    }
}
