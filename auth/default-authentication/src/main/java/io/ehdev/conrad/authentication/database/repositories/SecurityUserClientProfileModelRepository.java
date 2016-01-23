package io.ehdev.conrad.authentication.database.repositories;

import io.ehdev.conrad.authentication.database.model.SecurityUserClientProfileModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SecurityUserClientProfileModelRepository extends JpaRepository<SecurityUserClientProfileModel, UUID> {

}
