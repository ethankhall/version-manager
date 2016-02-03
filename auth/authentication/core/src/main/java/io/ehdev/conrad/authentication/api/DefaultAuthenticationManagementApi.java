package io.ehdev.conrad.authentication.api;


import io.ehdev.conrad.authentication.util.FilterUtilities;
import io.ehdev.conrad.database.impl.ModelConversionUtility;
import io.ehdev.conrad.database.model.user.ApiUser;
import io.ehdev.conrad.db.Tables;
import io.ehdev.conrad.db.tables.UserDetailsTable;
import io.ehdev.conrad.db.tables.UserSecurityClientProfileTable;
import io.ehdev.conrad.db.tables.pojos.UserDetails;
import org.jooq.DSLContext;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.oauth.profile.github.GitHubProfile;
import org.pac4j.oauth.profile.google2.Google2Profile;
import org.pac4j.springframework.security.authentication.ClientAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefaultAuthenticationManagementApi implements AuthenticationManagementApi {

    private final DSLContext dslContext;

    @Autowired
    public DefaultAuthenticationManagementApi(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public Authentication findAuthentication(ClientAuthenticationToken authenticationToken) {
        createNewUserIfNeeded(authenticationToken);
        UserProfile userProfile = authenticationToken.getUserProfile();

        UserDetailsTable ud = Tables.USER_DETAILS.as("ud");
        UserSecurityClientProfileTable uscp = Tables.USER_SECURITY_CLIENT_PROFILE.as("uscp");
        Optional<ApiUser> record = findUserDetails(userProfile, ud, uscp).map(ModelConversionUtility::toApiModel);

        if (record.isPresent()) {
            return FilterUtilities.createAuthentication(record.get());
        } else {
            return null;
        }
    }

    private Optional<UserDetails> findUserDetails(UserProfile userProfile,
                                                  UserDetailsTable ud,
                                                  UserSecurityClientProfileTable uscp) {
        return Optional.ofNullable(
            dslContext
                .select(ud.fields())
                .from(ud)
                .join(uscp).on(uscp.USER_UUID.eq(ud.UUID))
                .where(uscp.PROVIDER_TYPE.eq(userProfile.getClass().getSimpleName()))
                .and(uscp.PROVIDER_USER_ID.eq(userProfile.getId()))
                .fetchOne())
            .map(it -> it.into(UserDetails.class));
    }

    private void createNewUserIfNeeded(ClientAuthenticationToken authentication) {
        UserProfile userProfile = authentication.getUserProfile();

        UserDetailsTable ud = Tables.USER_DETAILS;
        UserSecurityClientProfileTable uscp = Tables.USER_SECURITY_CLIENT_PROFILE;

        Optional<UserDetails> userDetails = findUserDetails(userProfile, ud, uscp);

        if (!userDetails.isPresent()) {
            UserView newUserModel = createNewUserModel(userProfile);
            UserDetails details = dslContext
                .insertInto(ud, ud.NAME, ud.EMAIL_ADDRESS)
                .values(newUserModel.name, newUserModel.email)
                .returning(ud.fields())
                .fetchOne()
                .into(UserDetails.class);

            dslContext
                .insertInto(uscp, uscp.USER_UUID, uscp.PROVIDER_TYPE, uscp.PROVIDER_USER_ID)
                .values(details.getUuid(), userProfile.getClass().getSimpleName(), userProfile.getId())
                .execute();
        }
    }

    UserView createNewUserModel(UserProfile profile) {
        if (profile instanceof GitHubProfile) {
            return createUserModel((GitHubProfile) profile);
        } else if (profile instanceof Google2Profile) {
            return createUserModel((Google2Profile) profile);
        } else {
            throw new RuntimeException("Unsupported profile type: " + profile.getClass().getSimpleName());
        }
    }

    UserView createUserModel(GitHubProfile gitHubProfile) {
        return new UserView(gitHubProfile.getDisplayName(), gitHubProfile.getEmail());
    }

    UserView createUserModel(Google2Profile google2Profile) {
        return new UserView(google2Profile.getDisplayName(), google2Profile.getEmail());
    }

    private static class UserView {
        String name;
        String email;

        public UserView(String name, String email) {
            this.name = name;
            this.email = email;
        }
    }

}
