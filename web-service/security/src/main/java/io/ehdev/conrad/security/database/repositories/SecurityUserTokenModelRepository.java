package io.ehdev.conrad.security.database.repositories;

import io.ehdev.conrad.api.user.database.BaseUserModel;
import io.ehdev.conrad.security.database.model.SecurityUserTokenModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface SecurityUserTokenModelRepository extends JpaRepository<SecurityUserTokenModel, UUID> {
    @Query("select u.userModel from SecurityUserClientProfileModel u where u.providerType = :providerType and u.providerUserId = :providerUserId")
    BaseUserModel findOneByClientUserProfile(@Param("providerType") String providerType, @Param("providerUserId") String providerUserId);
}
