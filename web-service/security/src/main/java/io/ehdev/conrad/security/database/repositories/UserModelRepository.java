package io.ehdev.conrad.security.database.repositories;

import io.ehdev.conrad.security.database.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserModelRepository extends JpaRepository<UserModel, UUID> {

}
