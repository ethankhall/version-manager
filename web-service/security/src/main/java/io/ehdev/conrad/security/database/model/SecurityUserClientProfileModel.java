package io.ehdev.conrad.security.database.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.ehdev.conrad.api.user.database.BaseUserModel;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "security_user_client_profile")
public class SecurityUserClientProfileModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, unique = true, name = "uuid")
    private UUID id;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_uuid")
    private BaseUserModel userModel;

    @Column(name = "provider_type")
    private String providerType;

    @Column(name = "provider_user_id")
    private String providerUserId;

    public SecurityUserClientProfileModel() {

    }

    public SecurityUserClientProfileModel(BaseUserModel userModel, String providerType, String providerUserId) {
        this.userModel = userModel;
        this.providerType = providerType;
        this.providerUserId = providerUserId;
    }

    public BaseUserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(BaseUserModel userModel) {
        this.userModel = userModel;
    }

    public String getProviderType() {
        return providerType;
    }

    public void setProviderType(String providerType) {
        this.providerType = providerType;
    }

    public String getProviderUserId() {
        return providerUserId;
    }

    public void setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId;
    }
}
