package io.ehdev.conrad.security.database.repositories;

import io.ehdev.conrad.security.database.model.UserModel;
import io.ehdev.conrad.security.database.model.UserTokenModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface UserModelRepository extends JpaRepository<UserModel, UUID> {
    @Query("select u.userModel from ClientUserProfileModel u where u.providerType = :providerType and u.providerUserId = :providerUserId")
    UserModel findOneByClientUserProfile(@Param("providerType") String providerType, @Param("providerUserId") String providerUserId);
}
