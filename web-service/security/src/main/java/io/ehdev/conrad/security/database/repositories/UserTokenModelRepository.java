package io.ehdev.conrad.security.database.repositories;

import io.ehdev.conrad.security.database.model.UserTokenModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserTokenModelRepository extends JpaRepository<UserTokenModel, UUID> {
}
