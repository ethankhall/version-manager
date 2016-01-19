package io.ehdev.conrad.security.database.repositories;

import io.ehdev.conrad.security.database.model.SecurityUserTokenModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SecurityUserTokenModelRepository extends JpaRepository<SecurityUserTokenModel, UUID> {
}
