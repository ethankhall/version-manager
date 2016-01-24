package io.ehdev.conrad.database.impl.token;

import io.ehdev.conrad.database.impl.user.BaseUserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface UserTokenModelRepository extends JpaRepository<UserTokenModel, UUID> {
    @Query("select t.userModel from UserTokenModel t where t.id = :uuid")
    BaseUserModel findUserByToken(@Param("uuid") UUID uuid);
}
