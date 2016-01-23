package io.ehdev.conrad.authentication.database.repositories;

import io.ehdev.conrad.api.user.database.BaseUserModel;
import io.ehdev.conrad.authentication.database.model.SecurityUserClientProfileModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface SecurityUserClientProfileModelRepository extends JpaRepository<SecurityUserClientProfileModel, UUID> {
    @Query("select u.userModel from SecurityUserClientProfileModel u where u.providerType = :providerType and u.providerUserId = :providerUserId")
    BaseUserModel findOneByClientUserProfile(@Param("providerType") String providerType, @Param("providerUserId") String providerUserId);
}
