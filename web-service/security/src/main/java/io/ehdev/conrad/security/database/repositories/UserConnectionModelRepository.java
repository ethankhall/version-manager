package io.ehdev.conrad.security.database.repositories;

import io.ehdev.conrad.security.database.model.UserConnectionModel;
import io.ehdev.conrad.security.database.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface UserConnectionModelRepository extends JpaRepository<UserConnectionModel, UUID> {

    @Query("select c.userModel from UserConnectionModel c where c.connectionKey = :connectionKey")
    UserModel findByConnectionKey(@Param("connectionKey") String connectionKey);
}
