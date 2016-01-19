package io.ehdev.conrad.security.database.repositories;

import io.ehdev.conrad.security.database.model.ClientUserProfileModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClientUserProfileModelRepository extends JpaRepository<ClientUserProfileModel, UUID> {

}
