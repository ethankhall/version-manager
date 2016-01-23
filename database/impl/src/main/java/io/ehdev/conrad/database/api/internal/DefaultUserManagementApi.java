package io.ehdev.conrad.database.api.internal;

import io.ehdev.conrad.database.api.UserManagementApi;
import io.ehdev.conrad.database.internal.user.BaseUserModel;
import io.ehdev.conrad.database.internal.user.BaseUserRepository;
import io.ehdev.conrad.model.user.ConradUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultUserManagementApi implements UserManagementApi {

    final private BaseUserRepository baseUserRepository;

    @Autowired
    public DefaultUserManagementApi(BaseUserRepository baseUserRepository) {
        this.baseUserRepository = baseUserRepository;
    }

    @Override
    public ConradUser createUser(String name, String email) {
        BaseUserModel user = baseUserRepository.save(new BaseUserModel(name, email));
        return ModelConversionUtility.toApiModel(user);
    }
}
