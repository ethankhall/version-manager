package io.ehdev.conrad.database.api.internal;

import io.ehdev.conrad.database.api.TokenManagementApi;
import io.ehdev.conrad.database.api.exception.UserTokenNotFoundException;
import io.ehdev.conrad.database.impl.ModelConversionUtility;
import io.ehdev.conrad.database.impl.token.UserTokenModel;
import io.ehdev.conrad.database.impl.token.UserTokenModelRepository;
import io.ehdev.conrad.database.impl.user.BaseUserModel;
import io.ehdev.conrad.database.impl.user.BaseUserRepository;
import io.ehdev.conrad.model.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Service
public class DefaultTokenManagementApi implements TokenManagementApi {

    final private BaseUserRepository baseUserRepository;
    final private UserTokenModelRepository userTokenModelRepository;

    @Autowired
    public DefaultTokenManagementApi(BaseUserRepository baseUserRepository,
                                     UserTokenModelRepository userTokenModelRepository) {
        this.baseUserRepository = baseUserRepository;
        this.userTokenModelRepository = userTokenModelRepository;
    }

    @Override
    public ConradGeneratedToken createToken(ConradUser user, ConradTokenType type) {
        BaseUserModel baseUser = baseUserRepository.getOne(user.getUuid());
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneOffset.UTC).plusDays(30);
        UserTokenModel token = userTokenModelRepository.save(new UserTokenModel(baseUser, ModelConversionUtility.toDatabaseModel(type), zonedDateTime));
        return ModelConversionUtility.toApiModel(token);
    }

    @Override
    public boolean isTokenValid(ConradToken token) {
        UserTokenModel userToken = userTokenModelRepository.findOne(token.getUuid());
        return isValid(userToken);
    }

    private boolean isValid(UserTokenModel userToken) {
        return userToken != null
            && userToken.isValid()
            && ZonedDateTime.now(ZoneOffset.UTC).isBefore(userToken.getExpiresAt());
    }

    @Override
    public void invalidateTokenValid(ConradToken token) {
        UserTokenModel userToken = userTokenModelRepository.findOne(token.getUuid());
        if(userToken == null) {
            throw new UserTokenNotFoundException(token);
        }
        userToken.setValid(false);
        userTokenModelRepository.save(userToken);
    }

    @Override
    public ConradUser findUser(ConradToken token) {
        BaseUserModel user = userTokenModelRepository.findUserByToken(token.getUuid());
        if(isTokenValid(token)) {
            return ModelConversionUtility.toApiModel(user);
        } else {
            return null;
        }
    }
}
