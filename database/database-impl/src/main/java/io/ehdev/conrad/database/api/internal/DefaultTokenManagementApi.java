package io.ehdev.conrad.database.api.internal;

import io.ehdev.conrad.database.api.TokenManagementApi;
import io.ehdev.conrad.database.api.exception.UserTokenNotFoundException;
import io.ehdev.conrad.database.impl.ModelConversionUtility;
import io.ehdev.conrad.database.model.user.ApiGeneratedUserToken;
import io.ehdev.conrad.database.model.user.ApiToken;
import io.ehdev.conrad.database.model.user.ApiTokenType;
import io.ehdev.conrad.database.model.user.ApiUser;
import io.ehdev.conrad.db.Tables;
import io.ehdev.conrad.db.enums.TokenType;
import io.ehdev.conrad.db.tables.UserDetailsTable;
import io.ehdev.conrad.db.tables.UserTokensTable;
import io.ehdev.conrad.db.tables.daos.UserTokensDao;
import io.ehdev.conrad.db.tables.pojos.UserDetails;
import io.ehdev.conrad.db.tables.pojos.UserTokens;
import io.ehdev.conrad.db.tables.records.UserTokensRecord;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class DefaultTokenManagementApi implements TokenManagementApi {

    private final DSLContext dslContext;
    private final UserTokensDao userTokensDao;

    @Autowired
    public DefaultTokenManagementApi(DSLContext dslContext,
                                     UserTokensDao userTokensDao) {
        this.dslContext = dslContext;
        this.userTokensDao = userTokensDao;
    }

    @Override
    public ApiGeneratedUserToken createToken(ApiUser user, ApiTokenType type) {
        Instant now = Instant.now();
        UserTokensTable userTokens = Tables.USER_TOKENS;
        UserTokensRecord userTokensRecord = dslContext.insertInto(
            userTokens, userTokens.USER_UUID, userTokens.CREATED_AT, userTokens.EXPIRES_AT, userTokens.VALID, userTokens.TOKEN_TYPE)
            .values(user.getUuid(), now, now.plus(30, ChronoUnit.DAYS), true, TokenType.USER)
            .returning(userTokens.fields())
            .fetchOne();

        UserTokens into = userTokensRecord.into(UserTokens.class);
        return ModelConversionUtility.toApiModel(into);
    }

    @Override
    public boolean isTokenValid(ApiToken token) {
        UserTokens userToken = userTokensDao.fetchOneByUuid(token.getUuid());
        return isValid(userToken);
    }

    private boolean isValid(UserTokens userToken) {
        return userToken != null
            && userToken.getValid()
            && Instant.now().isBefore(userToken.getExpiresAt());
    }

    @Override
    public void invalidateTokenValid(ApiToken token) {
        UserTokens userToken = userTokensDao.fetchOneByUuid(token.getUuid());
        if (userToken == null) {
            throw new UserTokenNotFoundException(token);
        }
        userToken.setValid(false);
        userTokensDao.update(userToken);
    }

    @Override
    public ApiUser findUser(ApiToken token) {
        UserDetailsTable ud = Tables.USER_DETAILS.as("ud");
        UserTokensTable ut = Tables.USER_TOKENS.as("ut");
        //@formatter:off
        Optional<UserDetails> userDetails = Optional.ofNullable(dslContext
            .select(ud.fields())
            .from(ud)
            .join(ut)
                .on(ut.USER_UUID.eq(ud.UUID))
            .where(ut.UUID.eq(token.getUuid()))
                .and(ut.VALID.eq(true))
                .and(ut.EXPIRES_AT.greaterOrEqual(Instant.now()))
            .fetchOne())
            .map(it -> it.into(UserDetails.class));
        //@formatter:on

        if(userDetails.isPresent()) {
            return ModelConversionUtility.toApiModel(userDetails.get());
        } else {
            return null;
        }
    }
}
